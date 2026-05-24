package proseccovan.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proseccovan.backend.controller.booking.dto.BookingCreateRequestDto;
import proseccovan.backend.controller.booking.dto.BookingUpdateRequestDto;
import proseccovan.backend.controller.booking.dto.BookingResponseDto;
import proseccovan.backend.controller.booking.dto.BookingOverviewDto;
import proseccovan.backend.infrastructure.error.ErrorResponse;
import proseccovan.backend.infrastructure.exception.DataNotFoundException;
import proseccovan.backend.infrastructure.exception.ForbiddenException;
import proseccovan.backend.persistence.booking.Booking;
import proseccovan.backend.persistence.booking.BookingRepository;
import proseccovan.backend.persistence.booking.BookingStatus;
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



    public void createNewBooking(BookingCreateRequestDto request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(this::notFound);
        Package foundPackage = packageRepository.findByName(request.getPackageType())
                .orElseThrow(this::notFound);
        bookingRepository.save(Booking.builder()
                .user(user)
                .packageField(foundPackage)
                .address(request.getAddress())
                .eventDate(request.getBookingDate())
                .bookingTypeInfo(request.getBookingInfo())
                .status(BookingStatus.OOTEL.getAbbrev())
                .build());
    }

    public BookingResponseDto getBookingById(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(this::notFound);
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


    public List<BookingOverviewDto> getBookings(String status) {
        List<Booking> bookings = bookingRepository.findBookingsBy(status);
        List<BookingOverviewDto> result = new ArrayList<>();
        for (Booking booking : bookings) {
            result.add(toBookingOverviewDto(booking));
        }
        return result;
    }


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



    @Transactional
    public void updateBooking(Integer bookingId, BookingUpdateRequestDto request) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(this::notFound);
        Package bookingPackage = packageRepository.findByName(request.getPackageType())
                .orElseThrow(this::notFound);
        UserContact userContact = userContactRepository.findByUser_Id(booking.getUser().getId());

        booking.setEventDate(request.getBookingDate());
        booking.setAddress(request.getAddress());
        booking.setLatitude(request.getLatitude() != null ? new BigDecimal(request.getLatitude()) : null);
        booking.setLongitude(request.getLongitude() != null ? new BigDecimal(request.getLongitude()) : null);
        booking.setPackageField(bookingPackage);
        booking.getUser().setEmail(request.getEmail());
        userContact.setPhone(request.getPhoneNumber());
    }


    @Transactional
    public void confirmBooking(Integer bookingId) {
        changeBookingStatus(bookingId, BookingStatus.OOTEL, BookingStatus.KINNITATUD, CONFIRMATION_NOT_ALLOWED);
    }


    @Transactional
    public void cancelBooking(Integer bookingId) {
        changeBookingStatus(bookingId, BookingStatus.OOTEL, BookingStatus.TUHISTATUD, CANCELLATION_NOT_ALLOWED);
    }


    @Transactional
    public void adminCancelBooking(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(this::notFound);
        if (booking.getStatus().equals(BookingStatus.TUHISTATUD.getAbbrev())) {
            throw new ForbiddenException(CANCELLATION_NOT_ALLOWED.getMessage(), CANCELLATION_NOT_ALLOWED.getErrorCode());
        }
        booking.setStatus(BookingStatus.TUHISTATUD.getAbbrev());
        bookingRepository.save(booking);
    }

    private void changeBookingStatus(Integer bookingId, BookingStatus requiredStatus, BookingStatus newStatus, ErrorResponse error) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(this::notFound);
        if (!booking.getStatus().equals(requiredStatus.getAbbrev())) {
            throw new ForbiddenException(error.getMessage(), error.getErrorCode());
        }
        booking.setStatus(newStatus.getAbbrev());
        bookingRepository.save(booking);
    }

    private DataNotFoundException notFound() {
        return new DataNotFoundException(DATA_NOT_FOUND.getMessage(), DATA_NOT_FOUND.getErrorCode());
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