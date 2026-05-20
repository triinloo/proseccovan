package proseccovan.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import proseccovan.backend.controller.customerbooking.dto.BookingResponseDto;
import proseccovan.backend.infrastructure.exception.DataNotFoundException;
import proseccovan.backend.infrastructure.exception.ForbiddenException;
import proseccovan.backend.persistence.booking.Booking;
import proseccovan.backend.persistence.booking.BookingRepository;
import proseccovan.backend.persistence.booking.BookingStatusMapper;
import proseccovan.backend.persistence.usercontact.UserContact;
import proseccovan.backend.persistence.usercontact.UserContactRepository;

import java.time.format.DateTimeFormatter;

import static proseccovan.backend.infrastructure.error.ErrorResponse.CANCELLATION_NOT_ALLOWED;
import static proseccovan.backend.infrastructure.error.ErrorResponse.DATA_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CustomerBookingService {

    private final BookingRepository bookingRepository;
    private final UserContactRepository userContactRepository;

    public BookingResponseDto getBookingById(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new DataNotFoundException(DATA_NOT_FOUND.getMessage(), DATA_NOT_FOUND.getErrorCode()));

        UserContact userContact = userContactRepository.findByUser_Id(booking.getUser().getId());

        return new BookingResponseDto(
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

    public void cancelBooking(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new DataNotFoundException(DATA_NOT_FOUND.getMessage(), DATA_NOT_FOUND.getErrorCode()));

        if (!booking.getStatus().equals("O")) {
            throw new ForbiddenException(CANCELLATION_NOT_ALLOWED.getMessage(), CANCELLATION_NOT_ALLOWED.getErrorCode());
        }

        booking.setStatus("T");
        bookingRepository.save(booking);
    }
}