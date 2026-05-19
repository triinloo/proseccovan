package proseccovan.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import proseccovan.backend.controller.customerbooking.dto.BookingResponseDto;
import proseccovan.backend.infrastructure.exception.DataNotFoundException;
import proseccovan.backend.persistence.booking.Booking;
import proseccovan.backend.persistence.booking.BookingRepository;
import proseccovan.backend.persistence.booking.BookingStatusMapper;
import proseccovan.backend.persistence.usercontact.UserContact;
import proseccovan.backend.persistence.usercontact.UserContactRepository;

import java.time.format.DateTimeFormatter;

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

        BookingResponseDto dto = new BookingResponseDto();
        dto.setCustomerName(userContact.getUserName());
        dto.setEmail(booking.getUser().getEmail());
        dto.setPhoneNumber(userContact.getPhone());
        dto.setBookingDate(booking.getEventDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        dto.setBookingType(booking.getBookingTypeInfo());
        dto.setBookingPackageType(booking.getPackageField().getName());
        dto.setBookingAddress(booking.getAddress());
        dto.setLatitude(booking.getLatitude() != null ? booking.getLatitude().toPlainString() : null);
        dto.setLongitude(booking.getLongitude() != null ? booking.getLongitude().toPlainString() : null);
        dto.setInfo(booking.getPackageField().getDescription());
        dto.setBookingStatus(BookingStatusMapper.toBookingStatus(booking.getStatus()));
        return dto;
    }


}