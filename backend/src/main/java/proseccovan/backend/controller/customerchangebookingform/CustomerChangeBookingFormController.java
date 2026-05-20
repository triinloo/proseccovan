package proseccovan.backend.controller.customerchangebookingform;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import proseccovan.backend.controller.bookingform.dto.BookingCreateRequestDto;
import proseccovan.backend.controller.customerbooking.dto.BookingResponseDto;
import proseccovan.backend.infrastructure.error.ApiError;
import proseccovan.backend.service.CustomerBookingService;
import proseccovan.backend.service.CustomerChangeBookingFormService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CustomerChangeBookingFormController {

    private final CustomerBookingService customerBookingService;
    private final CustomerChangeBookingFormService customerChangeBookingFormService;

    @Operation(
            summary = "Broneeringu muutmisvormi andmete laadimine. Tagastab customerName, email, phoneNumber, bookingDate, bookingType, packageType, address, latitude, longitude, bookingInfo, bookingStatus",
            description = "Tagastab broneeringu andmed muutmisvormi eeltäitmiseks bookingId järgi. Kui broneeringut ei leita, visatakse viga errorCode'ga 333.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404",
                    description = "Broneeringut ei leitud",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/customer-bookings/{bookingId}")
    public BookingResponseDto getBookingForChangeForm(@PathVariable Integer bookingId) {
        return customerBookingService.getBookingById(bookingId);
    }

    @Operation(
            summary = "Broneeringu muutmine. Tagastab uuendatud customerName, email, phoneNumber, bookingDate, bookingType, packageType, address, latitude, longitude, bookingInfo, bookingStatus",
            description = "Uuendab broneeringu andmed bookingId järgi. Kui broneeringut ei leita, visatakse viga errorCode'ga 333.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404",
                    description = "Broneeringut ei leitud",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PatchMapping("/customer-bookings/{bookingId}")
    public BookingResponseDto updateBooking(@PathVariable Integer bookingId,
                                            @RequestBody BookingCreateRequestDto request) {
        return customerChangeBookingFormService.updateBooking(bookingId, request);
    }
}