package proseccovan.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proseccovan.backend.controller.booking.dto.BookingCreateRequestDto;
import proseccovan.backend.controller.booking.dto.BookingResponseDto;
import proseccovan.backend.controller.booking.dto.BookingOverviewDto;
import proseccovan.backend.infrastructure.error.ErrorResponse;
import proseccovan.backend.infrastructure.exception.DataNotFoundException;
import proseccovan.backend.infrastructure.exception.ForbiddenException;
import proseccovan.backend.persistence.booking.Booking;
import proseccovan.backend.persistence.booking.BookingRepository;
import proseccovan.backend.persistence.booking.BookingStatusMapper;
import proseccovan.backend.persistence.bookingpackage.Package;
import proseccovan.backend.persistence.bookingpackage.PackageRepository;
import proseccovan.backend.persistence.user.User;
import proseccovan.backend.persistence.user.UserRepository;
import proseccovan.backend.persistence.usercontact.UserContact;
import proseccovan.backend.persistence.usercontact.UserContactRepository;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static proseccovan.backend.infrastructure.error.ErrorResponse.*;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final PackageRepository packageRepository;
    private final UserRepository userRepository;
    private final UserContactRepository userContactRepository;



    /**
     * Loob uue broneeringu staatusega O (ootel).
     * @throws ForbiddenException kui kasutajat ei leita (errorCode 333)
     * @throws DataNotFoundException kui paketti ei leita (errorCode 333)
     */
    public void createNewBooking(BookingCreateRequestDto request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ForbiddenException(DATA_NOT_FOUND.getMessage(), DATA_NOT_FOUND.getErrorCode()));
        Package foundPackage = packageRepository.findPackageByType(request.getPackageType())
                .orElseThrow(() -> new DataNotFoundException(DATA_NOT_FOUND.getMessage(), DATA_NOT_FOUND.getErrorCode()));
        bookingRepository.save(Booking.builder()
                .user(user)
                .packageField(foundPackage)
                .address(request.getAddress())
                .eventDate(request.getBookingDate())
                .bookingTypeInfo(request.getBookingInfo())
                .status("O")
                .build());
    }

    /**
     * Tagastab ühe broneeringu täisinfo koos kasutaja kontaktandmetega.
     * @throws DataNotFoundException kui broneeringut ei leita (errorCode 333)
     */
    public BookingResponseDto getBookingById(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new DataNotFoundException(DATA_NOT_FOUND.getMessage(), DATA_NOT_FOUND.getErrorCode()));
        UserContact userContact = userContactRepository.findByUser_Id(booking.getUser().getId());
        return new BookingResponseDto(
                String.format("B%04d", booking.getId()),
                userContact.getUserName(),
                booking.getUser().getEmail(),
                userContact.getPhone(),
                booking.getEventDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                booking.getBookingTypeInfo(),
                booking.getPackageField().getName(),
                booking.getAddress(),
                booking.getLatitude() != null ? booking.getLatitude().toPlainString() : null,
                booking.getLongitude() != null ? booking.getLongitude().toPlainString() : null,
                booking.getPackageField().getDescription(),
                BookingStatusMapper.toBookingStatus(booking.getStatus())
        );
    }

    /**
     * Tagastab kõik broneeringud filtreeritud staatuse järgi.
     * Staatus võib olla O (ootel), K (kinnitatud) või T (tühistatud).
     */
    public List<BookingOverviewDto> getBookings(String status) {
        List<Booking> bookings = bookingRepository.findBookingsBy(status);
        List<BookingOverviewDto> result = new ArrayList<>();
        for (Booking booking : bookings) {
            result.add(toBookingOverviewDto(booking));
        }
        return result;
    }

    /**
     * Tagastab kõik broneeringud kasutaja ID järgi.
     * @throws DataNotFoundException kui kasutajat ei leita (errorCode 333)
     */
    public List<BookingOverviewDto> getBookingsByUserId(Integer userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new DataNotFoundException(DATA_NOT_FOUND.getMessage(), DATA_NOT_FOUND.getErrorCode());
        }
        List<Booking> bookings = bookingRepository.findBookingsBy(userId);
        List<BookingOverviewDto> result = new ArrayList<>();
        for (Booking booking : bookings) {
            result.add(toBookingOverviewDto(booking));
        }
        return result;
    }


    /**
     * Uuendab broneeringu andmeid (kuupäev, aadress, pakett, kontaktandmed).
     * @throws DataNotFoundException kui broneeringut või paketti ei leita (errorCode 333)
     */
    @Transactional
    public void updateBooking(Integer bookingId, BookingCreateRequestDto request) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new DataNotFoundException(DATA_NOT_FOUND.getMessage(), DATA_NOT_FOUND.getErrorCode()));
        Package bookingPackage = packageRepository.findByName(request.getPackageType())
                .orElseThrow(() -> new DataNotFoundException(DATA_NOT_FOUND.getMessage(), DATA_NOT_FOUND.getErrorCode()));
        UserContact userContact = userContactRepository.findByUser_Id(booking.getUser().getId());

        booking.setEventDate(request.getBookingDate());
        booking.setAddress(request.getAddress());
        booking.setLatitude(request.getLatitude() != null ? new BigDecimal(request.getLatitude()) : null);
        booking.setLongitude(request.getLongitude() != null ? new BigDecimal(request.getLongitude()) : null);
        booking.setPackageField(bookingPackage);
        booking.getUser().setEmail(request.getEmail());
        userContact.setPhone(request.getPhoneNumber());
    }

    /**
     * Kinnitab broneeringu, muutes staatuse O → K.
     * @throws DataNotFoundException kui broneeringut ei leita (errorCode 333)
     * @throws ForbiddenException kui broneeringu staatus ei ole O (errorCode 555)
     */
    public void confirmBooking(Integer bookingId) {
        changeBookingStatus(bookingId, "O", "K", CONFIRMATION_NOT_ALLOWED);
    }

    /**
     * Tühistab broneeringu kasutaja poolt, muutes staatuse O → T.
     * @throws DataNotFoundException kui broneeringut ei leita (errorCode 333)
     * @throws ForbiddenException kui broneeringu staatus ei ole O (errorCode 444)
     */
    public void cancelBooking(Integer bookingId) {
        changeBookingStatus(bookingId, "O", "T", CANCELLATION_NOT_ALLOWED);
    }

    /**
     * Tühistab broneeringu admini poolt, muutes staatuse T.
     * Lubatud kõigi staatuste puhul peale T (juba tühistatud).
     * @throws DataNotFoundException kui broneeringut ei leita (errorCode 333)
     * @throws ForbiddenException kui broneeringu staatus on juba T (errorCode 444)
     */
    public void adminCancelBooking(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new DataNotFoundException(DATA_NOT_FOUND.getMessage(), DATA_NOT_FOUND.getErrorCode()));
        if (booking.getStatus().equals("T")) {
            throw new ForbiddenException(CANCELLATION_NOT_ALLOWED.getMessage(), CANCELLATION_NOT_ALLOWED.getErrorCode());
        }
        booking.setStatus("T");
        bookingRepository.save(booking);
    }

    private void changeBookingStatus(Integer bookingId, String requiredStatus, String newStatus, ErrorResponse error) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new DataNotFoundException(DATA_NOT_FOUND.getMessage(), DATA_NOT_FOUND.getErrorCode()));
        if (!booking.getStatus().equals(requiredStatus)) {
            throw new ForbiddenException(error.getMessage(), error.getErrorCode());
        }
        booking.setStatus(newStatus);
        bookingRepository.save(booking);
    }

    private BookingOverviewDto toBookingOverviewDto(Booking booking) {
        UserContact userContact = userContactRepository.findByUser_Id(booking.getUser().getId());
        return new BookingOverviewDto(
                booking.getId(),
                String.format("B%04d", booking.getId()),
                userContact.getUserName(),
                booking.getEventDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                booking.getBookingTypeInfo(),
                booking.getAddress(),
                booking.getPackageField().getName(),
                BookingStatusMapper.toBookingStatus(booking.getStatus())
        );
    }
}