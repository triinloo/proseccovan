package proseccovan.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class BookingFormService {

    private final PackageRepository packageRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createNewBooking(BookingCreateRequestDto dto) {
        Package pkg = packageRepository.findByName(dto.getPackageType())
                .orElseThrow(() -> new DataNotFoundException(
                        ErrorResponse.PACKAGE_NOT_FOUND.getMessage(),
                        ErrorResponse.PACKAGE_NOT_FOUND.getErrorCode()));

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new DataNotFoundException(
                        ErrorResponse.DATA_NOT_FOUND.getMessage(),
                        ErrorResponse.DATA_NOT_FOUND.getErrorCode()));

        LocalDate eventDate = LocalDate.parse(dto.getBookingDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setPackageField(pkg);
        booking.setAddress(dto.getAddress());
        booking.setLatitude(new BigDecimal(dto.getLatitude()));
        booking.setLongitude(new BigDecimal(dto.getLongitude()));
        booking.setEventDate(eventDate);
        booking.setBookingTypeInfo(dto.getBookingType());
        booking.setStatus("P");

        bookingRepository.save(booking);
    }
}
