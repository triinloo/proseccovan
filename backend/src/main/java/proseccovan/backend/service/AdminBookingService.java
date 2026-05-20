package proseccovan.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import proseccovan.backend.controller.customerbookings.dto.BookingSummaryDto;
import proseccovan.backend.persistence.booking.Booking;
import proseccovan.backend.persistence.booking.BookingRepository;
import proseccovan.backend.persistence.booking.BookingStatusMapper;
import proseccovan.backend.persistence.usercontact.UserContact;
import proseccovan.backend.persistence.usercontact.UserContactRepository;

import proseccovan.backend.infrastructure.exception.DataNotFoundException;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static proseccovan.backend.infrastructure.error.ErrorResponse.DATA_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AdminBookingService {

    private final BookingRepository bookingRepository;
    private final UserContactRepository userContactRepository;

    public BookingSummaryDto confirmBooking(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new DataNotFoundException(DATA_NOT_FOUND.getMessage(), DATA_NOT_FOUND.getErrorCode()));
        booking.setStatus("K");
        bookingRepository.save(booking);
        UserContact userContact = userContactRepository.findByUser_Id(booking.getUser().getId());
        return toBookingSummaryDto(booking, userContact);
    }

    public BookingSummaryDto cancelBooking(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new DataNotFoundException(DATA_NOT_FOUND.getMessage(), DATA_NOT_FOUND.getErrorCode()));
        booking.setStatus("T");
        bookingRepository.save(booking);
        UserContact userContact = userContactRepository.findByUser_Id(booking.getUser().getId());
        return toBookingSummaryDto(booking, userContact);
    }

    public List<BookingSummaryDto> getAdminBookings() {
        return bookingRepository.findAll().stream()
                .map(booking -> {
                    UserContact userContact = userContactRepository.findByUser_Id(booking.getUser().getId());
                    return toBookingSummaryDto(booking, userContact);
                })
                .toList();
    }

    private BookingSummaryDto toBookingSummaryDto(Booking booking, UserContact userContact) {
        return new BookingSummaryDto(
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
