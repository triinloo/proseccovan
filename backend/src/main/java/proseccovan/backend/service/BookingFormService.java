package proseccovan.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import proseccovan.backend.controller.bookingform.dto.BookingCreateRequestDto;
import proseccovan.backend.infrastructure.exception.DataNotFoundException;
import proseccovan.backend.infrastructure.exception.ForbiddenException;
import proseccovan.backend.persistence.booking.Booking;
import proseccovan.backend.persistence.booking.BookingRepository;
import proseccovan.backend.persistence.bookingpackage.Package;
import proseccovan.backend.persistence.bookingpackage.PackageRepository;
import proseccovan.backend.persistence.user.User;
import proseccovan.backend.persistence.user.UserRepository;

import static proseccovan.backend.infrastructure.error.ErrorResponse.DATA_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BookingFormService {

    private final UserRepository userRepository;
    private final PackageRepository packageRepository;
    private final BookingRepository bookingRepository;


    public void createNewBooking(BookingCreateRequestDto bookingCreateRequestDto) {
        User user = userRepository.findById(bookingCreateRequestDto.getUserId())
                .orElseThrow(() -> new ForbiddenException(DATA_NOT_FOUND.getMessage(), DATA_NOT_FOUND.getErrorCode()));
        Package foundPackage = packageRepository.findPackageByType(bookingCreateRequestDto.getPackageType())
                .orElseThrow(() -> new DataNotFoundException(DATA_NOT_FOUND.getMessage(), DATA_NOT_FOUND.getErrorCode()));
        Booking booking = Booking.builder()
                .user(user)
                .packageField(foundPackage)
                .address(bookingCreateRequestDto.getAddress())
                .eventDate(bookingCreateRequestDto.getBookingDate())
                .bookingTypeInfo(bookingCreateRequestDto.getBookingInfo())
                .status("O")
                .build();

        bookingRepository.save(booking);




    }

}
