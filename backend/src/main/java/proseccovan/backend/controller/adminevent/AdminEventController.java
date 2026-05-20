package proseccovan.backend.controller.adminevent;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proseccovan.backend.controller.adminevent.dto.EventDetailResponseDto;
import proseccovan.backend.controller.adminevent.dto.EventRequestDto;
import proseccovan.backend.controller.events.dto.EventListResponseDto;
import proseccovan.backend.infrastructure.error.ApiError;
import proseccovan.backend.service.AdminEventService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminEventController {

    private final AdminEventService adminEventService;

    @Operation(
            summary = "Uue sündmuse loomine. Tagastab eventId, eventName, eventStartDate, eventEndDate, eventLocation, eventDescription, imageData",
            description = "Loob uue sündmuse andmebaasi. Nõuab userId välja, mis vastab olemasolevale kasutajale. Autentimine nõutav (TODO: Spring Security).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sündmus loodud"),
            @ApiResponse(responseCode = "404", description = "Kasutajat ei leitud",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "401", description = "Autentimata kasutaja",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping("/events")
    public ResponseEntity<EventDetailResponseDto> createEvent(@RequestBody EventRequestDto eventRequestDto) {
        EventDetailResponseDto response = adminEventService.createEvent(eventRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Ühe sündmuse andmed ID järgi. Tagastab eventId, eventName, eventStartDate, eventEndDate, eventLocation, eventDescription, imageData",
            description = "Tagastab ühe sündmuse andmed vormi eeltäitmiseks. Kui sündmust ei leita, tagastatakse 404 koos DATA_NOT_FOUND veaga. Autentimine nõutav (TODO: Spring Security).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Sündmust ei leitud",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "401", description = "Autentimata kasutaja",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/events/{eventId}")
    public EventDetailResponseDto getEventById(@PathVariable Integer eventId) {
        return adminEventService.getEventById(eventId);
    }

    @Operation(
            summary = "Admin sündmuste nimekiri. Tagastab eventId, eventName, eventDescription, eventStartDate, eventEndDate, eventLocation, imageData, eventSeason",
            description = "Tagastab kõik sündmused ilma filtrita. Mõeldud admin kasutajale (AdminEventsView). Autentimine nõutav (TODO: Spring Security).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Autentimata kasutaja",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/events")
    public List<EventListResponseDto> getAdminEvents() {
        return adminEventService.getAdminEvents();
    }

    @Operation(
            summary = "Uuenda sündmus ID järgi. Tagastab eventId, eventName, eventStartDate, eventEndDate, eventLocation, eventDescription, imageData",
            description = "Uuendab olemasoleva sündmuse kõik väljad peale created_by_user_id. Kui sündmust ei leita, tagastatakse 404 koos DATA_NOT_FOUND veaga. Autentimine nõutav (TODO: Spring Security).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sündmus uuendatud"),
            @ApiResponse(responseCode = "404", description = "Sündmust ei leitud",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "401", description = "Autentimata kasutaja",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping("/events/{eventId}")
    public EventDetailResponseDto updateEvent(@PathVariable Integer eventId, @RequestBody EventRequestDto eventRequestDto) {
        return adminEventService.updateEvent(eventId, eventRequestDto);
    }

    @Operation(
            summary = "Kustuta sündmus ID järgi",
            description = "Kustutab sündmuse andmebaasist antud eventId põhjal. Kui sündmust ei leita, tagastatakse 404 koos DATA_NOT_FOUND veaga. Autentimine nõutav (TODO: Spring Security).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sündmus kustutatud"),
            @ApiResponse(responseCode = "404", description = "Sündmust ei leitud",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "401", description = "Autentimata kasutaja",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<Void> deleteAdminEvent(@PathVariable Integer eventId) {
        adminEventService.deleteAdminEvent(eventId);
        return ResponseEntity.noContent().build();
    }
}
