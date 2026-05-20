package proseccovan.backend.controller.customerbooking;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import proseccovan.backend.controller.customerbooking.dto.BookingResponseDto;
import proseccovan.backend.infrastructure.error.ApiError;
import proseccovan.backend.service.CustomerBookingService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CustomerBookingController {

    private final CustomerBookingService customerBookingService;

    @Operation(
            summary = "Broneeringu detailvaade. Tagastab customerName, email, phoneNumber, bookingDate, bookingType, bookingPackageType, bookingAddress, latitude, longitude, info, bookingStatus",
            description = "Tagastab ühe broneeringu kõik detailid bookingId järgi. Kui broneeringut ei leita, visatakse viga errorCode'ga 333.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404",
                    description = "Broneeringut ei leitud",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/customer-bookings/{bookingId}")
    public BookingResponseDto getBookingById(@PathVariable Integer bookingId) {
        return customerBookingService.getBookingById(bookingId);
    }
}