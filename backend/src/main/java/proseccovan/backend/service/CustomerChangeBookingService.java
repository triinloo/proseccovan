package proseccovan.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import proseccovan.backend.controller.customerbooking.dto.BookingResponseDto;
import proseccovan.backend.controller.customerchangebooking.dto.BookingRequestDto;
import proseccovan.backend.infrastructure.exception.DataNotFoundException;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static proseccovan.backend.infrastructure.error.ErrorResponse.DATA_NOT_FOUND;
import static proseccovan.backend.infrastructure.error.ErrorResponse.PACKAGE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CustomerChangeBookingService {

    private final BookingRepository bookingRepository;
    private final PackageRepository packageRepository;
    private final UserRepository userRepository;
    private final UserContactRepository userContactRepository;

    public BookingResponseDto updateBooking(Integer bookingId, BookingRequestDto dto) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new DataNotFoundException(DATA_NOT_FOUND.getMessage(), DATA_NOT_FOUND.getErrorCode()));

        Package bookingPackage = packageRepository.findByName(dto.getPackageType())
                .orElseThrow(() -> new DataNotFoundException(PACKAGE_NOT_FOUND.getMessage(), PACKAGE_NOT_FOUND.getErrorCode()));

        booking.setEventDate(LocalDate.parse(dto.getBookingDate()));
        booking.setBookingTypeInfo(dto.getBookingType());
        booking.setAddress(dto.getAddress());
        booking.setLatitude(dto.getLatitude() != null ? new BigDecimal(dto.getLatitude()) : null);
        booking.setLongitude(dto.getLongitude() != null ? new BigDecimal(dto.getLongitude()) : null);
        booking.setPackageField(bookingPackage);
        bookingRepository.save(booking);

        UserContact userContact = userContactRepository.findByUser_Id(booking.getUser().getId());
        userContact.setUserName(dto.getCustomerName());
        userContact.setPhone(dto.getPhoneNumber());
        userContactRepository.save(userContact);

        User user = booking.getUser();
        user.setEmail(dto.getEmail());
        userRepository.save(user);

        BookingResponseDto response = new BookingResponseDto();
        response.setCustomerName(userContact.getUserName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(userContact.getPhone());
        response.setBookingDate(booking.getEventDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        response.setBookingType(booking.getBookingTypeInfo());
        response.setBookingPackageType(bookingPackage.getName());
        response.setBookingAddress(booking.getAddress());
        response.setLatitude(booking.getLatitude() != null ? booking.getLatitude().toPlainString() : null);
        response.setLongitude(booking.getLongitude() != null ? booking.getLongitude().toPlainString() : null);
        response.setInfo(bookingPackage.getDescription());
        response.setBookingStatus(BookingStatusMapper.toBookingStatus(booking.getStatus()));
        return response;
    }
}
