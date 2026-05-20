package proseccovan.backend.controller.bookingform;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proseccovan.backend.controller.bookingform.dto.BookingCreateRequestDto;
import proseccovan.backend.infrastructure.error.ApiError;
import proseccovan.backend.service.BookingFormService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookingFormController {

    private final BookingFormService bookingFormService;

    @Operation(summary = "Uue broneeringu loomine",
            description = """
                    Loob uue broneeringu kasutaja ID, paketi tüübi ja broneeringu andmete põhjal.
                    Kui kasutajat ei leita, visatakse viga errorCode'ga 333.
                    Kui paketti ei leita, visatakse viga errorCode'ga 333.""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403",
                    description = "Kasutajat ei leitud",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404",
                    description = "Paketti ei leitud",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))})

    @PostMapping("/booking-form")
    public void createNewBooking(@RequestBody BookingCreateRequestDto bookingCreateRequestDto) {
        bookingFormService.createNewBooking(bookingCreateRequestDto);
    }
}
