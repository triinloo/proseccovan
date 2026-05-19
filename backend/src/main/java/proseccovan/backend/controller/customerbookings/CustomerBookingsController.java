package proseccovan.backend.controller.customerbookings;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import proseccovan.backend.controller.customerbookings.dto.BookingSummaryDto;
import proseccovan.backend.infrastructure.error.ApiError;
import proseccovan.backend.service.CustomerBookingsService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CustomerBookingsController {

    private final CustomerBookingsService customerBookingsService;

    @Operation(
            summary = "Kliendi broneeringute nimekiri. Tagastab bookingId, customerName, bookingDate, bookingType, location, packageType, bookingStatus",
            description = "Tagastab kõik broneeringud antud userId kohta. Kui kasutajat ei leita, visatakse viga errorCode'ga 333.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404",
                    description = "Kasutaja ei leitud",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/bookings/user/{userId}")
    public List<BookingSummaryDto> getBookingsByUserId(@PathVariable Integer userId) {
        return customerBookingsService.getBookingsByUserId(userId);
    }
}