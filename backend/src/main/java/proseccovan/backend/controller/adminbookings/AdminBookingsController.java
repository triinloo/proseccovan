package proseccovan.backend.controller.adminbookings;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import proseccovan.backend.controller.customerbooking.dto.BookingResponseDto;
import proseccovan.backend.controller.customerbookings.dto.BookingSummaryDto;
import proseccovan.backend.infrastructure.error.ApiError;
import proseccovan.backend.service.AdminBookingsService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AdminBookingsController {

    private final AdminBookingsService adminBookingsService;

    @GetMapping("/admin-bookings")
    @Operation(
            summary = "Kõikide broneeringute nimekiri.",
            description = "Tagastab kõik broneeringud. Kui status on 'A' (ALL), siis statuse järgi filtreerimist ei toimu, vaid leitakse kõik broneeringud.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public List<BookingSummaryDto> getBookings(@RequestParam String status) {
        return adminBookingsService.getBookings(status);
    }

    @GetMapping("/admin-bookings/{bookingId}")
    @Operation(
            summary = "Broneeringu detailvaade (admin). Tagastab customerName, email, phoneNumber, bookingDate, bookingType, packageType, address, latitude, longitude, bookingInfo, bookingStatus",
            description = "Tagastab ühe broneeringu kõik detailid bookingId järgi. Kui broneeringut ei leita, visatakse viga errorCode'ga 333.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404",
                    description = "Broneeringut ei leitud",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public BookingResponseDto getBookingById(@PathVariable Integer bookingId) {
        return adminBookingsService.getBookingById(bookingId);
    }

    @Operation(
            summary = "Broneeringu kinnitamine",
            description = "Muudab broneeringu staatuse OOTEL → KINNITATUD. Kui broneeringut ei leita, visatakse viga errorCode'ga 333. Kui staatus ei ole OOTEL, visatakse viga errorCode'ga 555.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404",
                    description = "Broneeringut ei leitud",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "403",
                    description = "Broneeringut ei saa kinnitada",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping("/admin-bookings/{bookingId}/confirm")
    public void confirmBooking(@PathVariable Integer bookingId) {
        adminBookingsService.confirmBooking(bookingId);
    }

    @Operation(
            summary = "Broneeringu tühistamine",
            description = "Muudab broneeringu staatuse OOTEL/KINNITATUD → TÜHISTATUD. Kui broneeringut ei leita, visatakse viga errorCode'ga 333. Kui broneering on juba tühistatud, visatakse viga errorCode'ga 444.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404",
                    description = "Broneeringut ei leitud",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "403",
                    description = "Broneeringut ei saa tühistada",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping("/admin-bookings/{bookingId}/cancel")
    public void cancelBooking(@PathVariable Integer bookingId) {
        adminBookingsService.cancelBooking(bookingId);
    }
}