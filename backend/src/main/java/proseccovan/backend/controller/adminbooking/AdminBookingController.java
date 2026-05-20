package proseccovan.backend.controller.adminbooking;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proseccovan.backend.controller.customerbookings.dto.BookingSummaryDto;
import proseccovan.backend.infrastructure.error.ApiError;
import proseccovan.backend.service.AdminBookingService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminBookingController {

    private final AdminBookingService adminBookingService;

    @Operation(
            summary = "Admin broneeringute nimekiri. Tagastab bookingId, customerName, bookingDate, bookingType, location, packageType, bookingStatus",
            description = "Tagastab kõik broneeringud kõigi kasutajate kohta. Tühi tulemus tagastab HTTP 200 koos tühja massiiviga. Autentimine nõutav (TODO: Spring Security).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Autentimata kasutaja",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/bookings")
    public List<BookingSummaryDto> getAdminBookings() {
        return adminBookingService.getAdminBookings();
    }

    @Operation(
            summary = "Kinnita broneering ID järgi. Tagastab bookingId, customerName, bookingDate, bookingType, location, packageType, bookingStatus",
            description = "Uuendab broneeringu staatuse KINNITATUD-ks. Kui broneeringut ei leita, tagastatakse 404 koos DATA_NOT_FOUND veaga. Autentimine nõutav (TODO: Spring Security).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Broneering kinnitatud"),
            @ApiResponse(responseCode = "404", description = "Broneeringut ei leitud",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "401", description = "Autentimata kasutaja",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping("/bookings/{bookingId}/confirm")
    public BookingSummaryDto confirmBooking(@PathVariable Integer bookingId) {
        return adminBookingService.confirmBooking(bookingId);
    }

    @Operation(
            summary = "Tühista broneering ID järgi. Tagastab bookingId, customerName, bookingDate, bookingType, location, packageType, bookingStatus",
            description = "Uuendab broneeringu staatuse TÜHISTATUD-ks. Kui broneeringut ei leita, tagastatakse 404 koos DATA_NOT_FOUND veaga. Autentimine nõutav (TODO: Spring Security).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Broneering tühistatud"),
            @ApiResponse(responseCode = "404", description = "Broneeringut ei leitud",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "401", description = "Autentimata kasutaja",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping("/bookings/{bookingId}/cancel")
    public BookingSummaryDto cancelBooking(@PathVariable Integer bookingId) {
        return adminBookingService.cancelBooking(bookingId);
    }
}
