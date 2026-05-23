package proseccovan.backend.controller.booking;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import proseccovan.backend.controller.booking.dto.BookingCreateRequestDto;
import proseccovan.backend.controller.booking.dto.BookingResponseDto;
import proseccovan.backend.controller.booking.dto.BookingOverviewDto;
import proseccovan.backend.infrastructure.error.ApiError;
import proseccovan.backend.service.BookingService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;


    @GetMapping("/admin-bookings")
    @Operation(summary = "Kõikide broneeringute nimekiri. Tagastab BookingOverviewDto nimekirja",
            description = """
                    Tagastab kõik broneeringud filtreeritud staatuse järgi.
                    Staatus võib olla O (ootel), K (kinnitatud) või T (tühistatud).""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")})

    public List<BookingOverviewDto> getBookings(@RequestParam(required = false) String status) {
        return bookingService.getBookings(status);
    }


    @GetMapping("/admin-bookings/{bookingId}")
    @Operation(summary = "Ühe broneeringu täisinfo. Tagastab BookingResponseDto",
            description = """
                    Otsitakse broneeringut ID järgi.
                    Kui broneeringut ei leita, visatakse viga errorCode'ga 333.""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404",
                    description = "Broneeringut ei leitud",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))})

    public BookingResponseDto getAdminBookingById(@PathVariable Integer bookingId) {
        return bookingService.getBookingById(bookingId);
    }


    @PutMapping("/admin-bookings/{bookingId}/confirm")
    @Operation(summary = "Broneeringu kinnitamine. Muudab staatuse K (kinnitatud)",
            description = """
                    Otsitakse broneeringut ID järgi ja muudetakse staatus O → K.
                    Kui broneeringut ei leita, visatakse viga errorCode'ga 333.
                    Kui broneeringu staatus ei ole O (ootel), visatakse viga errorCode'ga 555.""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404",
                    description = "Broneeringut ei leitud",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "403",
                    description = "Broneeringut ei saa kinnitada",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))})

    public void confirmBooking(@PathVariable Integer bookingId) {
        bookingService.confirmBooking(bookingId);
    }


    @PutMapping("/admin-bookings/{bookingId}/cancel")
    @Operation(summary = "Broneeringu tühistamine admini poolt. Muudab staatuse T (tühistatud)",
            description = """
                    Otsitakse broneeringut ID järgi ja muudetakse staatus T.
                    Kui broneeringut ei leita, visatakse viga errorCode'ga 333.
                    Kui broneeringu staatus on juba T (tühistatud), visatakse viga errorCode'ga 444.""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404",
                    description = "Broneeringut ei leitud",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "403",
                    description = "Broneeringut ei saa tühistada",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))})

    public void adminCancelBooking(@PathVariable Integer bookingId) {
        bookingService.adminCancelBooking(bookingId);
    }


    @GetMapping("/bookings/user/{userId}")
    @Operation(summary = "Kasutaja broneeringute nimekiri. Tagastab BookingOverviewDto nimekirja",
            description = """
                    Tagastab kõik broneeringud kasutaja ID järgi.
                    Kui kasutajat ei leita, visatakse viga errorCode'ga 333.""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404",
                    description = "Kasutajat ei leitud",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))})

    public List<BookingOverviewDto> getBookingsByUserId(@PathVariable Integer userId) {
        return bookingService.getBookingsByUserId(userId);
    }


    @PostMapping("/booking-form")
    @Operation(summary = "Uue broneeringu loomine",
            description = """
                    Loob uue broneeringu staatusega O (ootel).
                    Kui kasutajat ei leita, visatakse viga errorCode'ga 333.
                    Kui paketti ei leita, visatakse viga errorCode'ga 333.""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403",
                    description = "Kasutajat või paketti ei leitud",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))})

    public void createNewBooking(@RequestBody BookingCreateRequestDto bookingCreateRequestDto) {
        bookingService.createNewBooking(bookingCreateRequestDto);
    }


    @GetMapping("/customer-booking/{bookingId}")
    @Operation(summary = "Ühe broneeringu täisinfo kasutajale. Tagastab BookingResponseDto",
            description = """
                    Otsitakse broneeringut ID järgi.
                    Kui broneeringut ei leita, visatakse viga errorCode'ga 333.""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404",
                    description = "Broneeringut ei leitud",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))})

    public BookingResponseDto getBookingById(@PathVariable Integer bookingId) {
        return bookingService.getBookingById(bookingId);
    }

    @PatchMapping("/customer-booking/{bookingId}")
    @Operation(summary = "Broneeringu andmete muutmine",
            description = """
                    Uuendab olemasoleva broneeringu andmeid (kuupäev, aadress, pakett, kontaktandmed).
                    Kui broneeringut ei leita, visatakse viga errorCode'ga 333.
                    Kui paketti ei leita, visatakse viga errorCode'ga 333.""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404",
                    description = "Broneeringut või paketti ei leitud",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))})

    public void updateBooking(@PathVariable Integer bookingId,
                              @RequestBody BookingCreateRequestDto request) {
        bookingService.updateBooking(bookingId, request);
    }


    @DeleteMapping("/customer-booking/{bookingId}")
    @Operation(summary = "Broneeringu tühistamine kasutaja poolt. Muudab staatuse T (tühistatud)",
            description = """
                    Otsitakse broneeringut ID järgi ja muudetakse staatus O → T.
                    Kui broneeringut ei leita, visatakse viga errorCode'ga 333.
                    Kui broneeringu staatus ei ole O (ootel), visatakse viga errorCode'ga 444.""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404",
                    description = "Broneeringut ei leitud",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "403",
                    description = "Broneeringut ei saa tühistada",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))})

    public void cancelBooking(@PathVariable Integer bookingId) {
        bookingService.cancelBooking(bookingId);
    }
}