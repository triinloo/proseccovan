package proseccovan.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import proseccovan.backend.controller.bookingform.dto.BookingCreateRequestDto;
import proseccovan.backend.persistence.booking.BookingRepository;
import proseccovan.backend.persistence.bookingpackage.PackageRepository;
import proseccovan.backend.persistence.user.UserRepository;

@Service
@RequiredArgsConstructor
public class BookingFormService {

    private final UserRepository userRepository;
    private final PackageRepository packageRepository;
    private final BookingRepository bookingRepository;


    public void createNewBooking(BookingCreateRequestDto bookingCreateRequestDto) {
        // TODO: implementeerida
    }

}
