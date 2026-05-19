package proseccovan.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import proseccovan.backend.controller.customerbookings.dto.BookingSummaryDto;
import proseccovan.backend.infrastructure.exception.DataNotFoundException;
import proseccovan.backend.persistence.booking.Booking;
import proseccovan.backend.persistence.booking.BookingRepository;
import proseccovan.backend.persistence.user.UserRepository;
import proseccovan.backend.persistence.usercontact.UserContact;
import proseccovan.backend.persistence.usercontact.UserContactRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static proseccovan.backend.infrastructure.error.ErrorResponse.DATA_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CustomerBookingsService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final UserContactRepository userContactRepository;

    public List<BookingSummaryDto> getBookingsByUserId(Integer userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new DataNotFoundException(DATA_NOT_FOUND.getMessage(), DATA_NOT_FOUND.getErrorCode());
        }

        UserContact userContact = userContactRepository.findByUser_Id(userId);
        List<Booking> bookings = bookingRepository.findByUser_Id(userId);

        return bookings.stream()
                .map(booking -> toBookingSummaryDto(booking, userContact))
                .toList();
    }

    private BookingSummaryDto toBookingSummaryDto(Booking booking, UserContact userContact) {
        BookingSummaryDto dto = new BookingSummaryDto();
        dto.setBookingId(String.format("B%04d", booking.getId()));
        dto.setCustomerName(userContact.getUserName());
        dto.setBookingDate(booking.getEventDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        dto.setBookingType(booking.getBookingTypeInfo());
        dto.setLocation(booking.getAddress());
        dto.setPackageType(booking.getPackageField().getName());
        dto.setBookingStatus(toBookingStatus(booking.getStatus()));
        return dto;
    }

    private String toBookingStatus(String status) {
        return switch (status) {
            case "O" -> "OOTEL";
            case "K" -> "KINNITATUD";
            case "T" -> "TÜHISTATUD";
            default -> status;
        };
    }
}