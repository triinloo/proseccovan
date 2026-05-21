package proseccovan.backend.controller.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import proseccovan.backend.controller.booking.dto.BookingCreateRequestDto;
import proseccovan.backend.controller.booking.dto.BookingResponseDto;
import proseccovan.backend.controller.booking.dto.BookingOverviewDto;
import proseccovan.backend.service.BookingService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/admin-bookings")
    public List<BookingOverviewDto> getBookings(@RequestParam String status) {
        return bookingService.getBookings(status);
    }

    @GetMapping("/admin-bookings/{bookingId}")
    public BookingResponseDto getAdminBookingById(@PathVariable Integer bookingId) {
        return bookingService.getBookingById(bookingId);
    }

    @PutMapping("/admin-bookings/{bookingId}/confirm")
    public void confirmBooking(@PathVariable Integer bookingId) {
        bookingService.confirmBooking(bookingId);
    }

    @PutMapping("/admin-bookings/{bookingId}/cancel")
    public void adminCancelBooking(@PathVariable Integer bookingId) {
        bookingService.adminCancelBooking(bookingId);
    }

    @GetMapping("/bookings/user/{userId}")
    public List<BookingOverviewDto> getBookingsByUserId(@PathVariable Integer userId) {
        return bookingService.getBookingsByUserId(userId);
    }

    @PostMapping("/booking-form")
    public void createNewBooking(@RequestBody BookingCreateRequestDto bookingCreateRequestDto) {
        bookingService.createNewBooking(bookingCreateRequestDto);
    }

    @GetMapping("/customer-booking/{bookingId}")
    public BookingResponseDto getBookingById(@PathVariable Integer bookingId) {
        return bookingService.getBookingById(bookingId);
    }

    @PatchMapping("/customer-booking/{bookingId}")
    public void updateBooking(@PathVariable Integer bookingId,
                              @RequestBody BookingCreateRequestDto request) {
        bookingService.updateBooking(bookingId, request);
    }

    @DeleteMapping("/customer-booking/{bookingId}")
    public void cancelBooking(@PathVariable Integer bookingId) {
        bookingService.cancelBooking(bookingId);
    }
}