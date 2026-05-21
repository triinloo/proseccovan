
package proseccovan.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import proseccovan.backend.controller.bookingform.dto.BookingCreateRequestDto;
import proseccovan.backend.infrastructure.error.ErrorResponse;
import proseccovan.backend.infrastructure.exception.DataNotFoundException;
import proseccovan.backend.persistence.booking.Booking;
import proseccovan.backend.persistence.booking.BookingRepository;
import proseccovan.backend.persistence.bookingpackage.Package;
import proseccovan.backend.persistence.bookingpackage.PackageRepository;
import proseccovan.backend.persistence.user.User;
import proseccovan.backend.persistence.user.UserRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BookingFormService {

    private final UserRepository userRepository;
    private final PackageRepository packageRepository;
    private final BookingRepository bookingRepository;

    public void createNewBooking(BookingCreateRequestDto dto) {
        Package bookingPackage = packageRepository.findByName(dto.getPackageType())
                .orElseThrow(() -> new DataNotFoundException(
                        ErrorResponse.PACKAGE_NOT_FOUND.getMessage(),
                        ErrorResponse.PACKAGE_NOT_FOUND.getErrorCode()));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new DataNotFoundException(
                        ErrorResponse.DATA_NOT_FOUND.getMessage(),
                        ErrorResponse.DATA_NOT_FOUND.getErrorCode()));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setPackageField(bookingPackage);
        booking.setAddress(dto.getAddress());
        booking.setLatitude(new BigDecimal(dto.getLatitude()));
        booking.setLongitude(new BigDecimal(dto.getLongitude()));
        booking.setEventDate(dto.getBookingDate());
        booking.setBookingTypeInfo(dto.getBookingType());
        booking.setStatus("P");

        bookingRepository.save(booking);
    }

}