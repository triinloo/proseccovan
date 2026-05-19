package proseccovan.backend.controller.bookingform;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proseccovan.backend.controller.bookingform.dto.BookingCreateRequestDto;
import proseccovan.backend.service.BookingFormService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookingFormController {

    private final BookingFormService bookingFormService;

    @PostMapping("/booking-form")
    public void createNewBooking(@RequestBody BookingCreateRequestDto bookingCreateRequestDto) {

    }
}
