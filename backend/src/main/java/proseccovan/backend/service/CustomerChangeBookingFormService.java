package proseccovan.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proseccovan.backend.controller.bookingform.dto.BookingCreateRequestDto;
import proseccovan.backend.controller.customerbooking.dto.BookingResponseDto;
import proseccovan.backend.infrastructure.exception.DataNotFoundException;
import proseccovan.backend.persistence.booking.Booking;
import proseccovan.backend.persistence.booking.BookingRepository;
import proseccovan.backend.persistence.bookingpackage.Package;
import proseccovan.backend.persistence.bookingpackage.PackageRepository;
import proseccovan.backend.persistence.usercontact.UserContact;
import proseccovan.backend.persistence.usercontact.UserContactRepository;

import static proseccovan.backend.infrastructure.error.ErrorResponse.DATA_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CustomerChangeBookingFormService {

    private final BookingRepository bookingRepository;
    private final PackageRepository packageRepository;
    private final UserContactRepository userContactRepository;
    private final CustomerBookingService customerBookingService;

    @Transactional
    public BookingResponseDto updateBooking(Integer bookingId, BookingCreateRequestDto request) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new DataNotFoundException(DATA_NOT_FOUND.getMessage(), DATA_NOT_FOUND.getErrorCode()));

        Package bookingPackage = packageRepository.findByName(request.getPackageType())
                .orElseThrow(() -> new DataNotFoundException(DATA_NOT_FOUND.getMessage(), DATA_NOT_FOUND.getErrorCode()));
        UserContact userContact = userContactRepository.findByUser_Id(booking.getUser().getId());

        booking.setEventDate(request.getBookingDate());
        booking.setAddress(request.getAddress());
        booking.setLatitude(request.getLatitude() != null ? new java.math.BigDecimal(request.getLatitude()) : null);
        booking.setLongitude(request.getLongitude() != null ? new java.math.BigDecimal(request.getLongitude()) : null);
        booking.setPackageField(bookingPackage);
        bookingRepository.save(booking);

        booking.getUser().setEmail(request.getEmail());
        userContact.setPhone(request.getPhoneNumber());
        userContactRepository.save(userContact);

        return customerBookingService.getBookingById(bookingId);
    }
}