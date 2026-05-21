package proseccovan.backend.controller.customerchangebooking;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proseccovan.backend.controller.customerbooking.dto.BookingResponseDto;
import proseccovan.backend.controller.customerchangebooking.dto.BookingRequestDto;
import proseccovan.backend.infrastructure.error.ApiError;
import proseccovan.backend.service.CustomerChangeBookingService;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerChangeBookingController {

    private final CustomerChangeBookingService customerChangeBookingService;

    @Operation(
            summary = "Uuenda broneering ID järgi. Tagastab customerName, email, phoneNumber, bookingDate, bookingType, bookingPackageType, bookingAddress, latitude, longitude, info, bookingStatus",
            description = "Uuendab olemasoleva broneeringu kõik väljad. Kui broneeringut ei leita, tagastatakse 404 koos DATA_NOT_FOUND veaga. Kui paketti ei leita, tagastatakse 404 koos PACKAGE_NOT_FOUND veaga. Autentimine nõutav (TODO: Spring Security).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Broneering uuendatud"),
            @ApiResponse(responseCode = "404", description = "Broneeringut või paketti ei leitud",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "401", description = "Autentimata kasutaja",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PatchMapping("/bookings/{bookingId}")
    public BookingResponseDto updateBooking(@PathVariable Integer bookingId, @RequestBody BookingRequestDto bookingRequestDto) {
        return customerChangeBookingService.updateBooking(bookingId, bookingRequestDto);
    }
}
