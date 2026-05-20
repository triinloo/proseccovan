package proseccovan.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import proseccovan.backend.controller.customerbooking.dto.BookingResponseDto;
import proseccovan.backend.controller.customerbookings.dto.BookingSummaryDto;
import proseccovan.backend.infrastructure.exception.DataNotFoundException;
import proseccovan.backend.infrastructure.exception.ForbiddenException;
import proseccovan.backend.persistence.booking.Booking;
import proseccovan.backend.persistence.booking.BookingRepository;
import proseccovan.backend.persistence.booking.BookingStatusMapper;
import proseccovan.backend.persistence.usercontact.UserContact;
import proseccovan.backend.persistence.usercontact.UserContactRepository;

import static proseccovan.backend.infrastructure.error.ErrorResponse.*;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminBookingsService {

    private final BookingRepository bookingRepository;
    private final UserContactRepository userContactRepository;

    public List<BookingSummaryDto> getBookings(String status) {
        List<Booking> bookings = bookingRepository.findBookingsBy(status);

        List<BookingSummaryDto> result = new ArrayList<>();
        for (Booking booking : bookings) {
            result.add(toBookingSummaryDto(booking));
        }
        return result;
    }

    private BookingSummaryDto toBookingSummaryDto(Booking booking) {
        String customerName = userContactRepository.findByUser_Id(booking.getUser().getId()).getUserName();
        return new BookingSummaryDto(
                String.format("B%04d", booking.getId()),
                customerName,
                booking.getEventDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                booking.getBookingTypeInfo(),
                booking.getAddress(),
                booking.getPackageField().getName(),
                BookingStatusMapper.toBookingStatus(booking.getStatus())
        );
    }

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

    public void confirmBooking(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new DataNotFoundException(DATA_NOT_FOUND.getMessage(), DATA_NOT_FOUND.getErrorCode()));

        if (!booking.getStatus().equals("O")) {
            throw new ForbiddenException(CONFIRMATION_NOT_ALLOWED.getMessage(), CONFIRMATION_NOT_ALLOWED.getErrorCode());
        }

        booking.setStatus("K");
        bookingRepository.save(booking);
    }

    public void cancelBooking(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new DataNotFoundException(DATA_NOT_FOUND.getMessage(), DATA_NOT_FOUND.getErrorCode()));

        if (booking.getStatus().equals("T")) {
            throw new ForbiddenException(CANCELLATION_NOT_ALLOWED.getMessage(), CANCELLATION_NOT_ALLOWED.getErrorCode());
        }

        booking.setStatus("T");
        bookingRepository.save(booking);
    }


}