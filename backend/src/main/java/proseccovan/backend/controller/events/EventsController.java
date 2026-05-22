package proseccovan.backend.controller.events;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import proseccovan.backend.controller.events.dto.EventListResponseDto;
import proseccovan.backend.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EventsController {

    private final EventService eventService;

    @GetMapping("/events")
    @Operation(
            summary = "Sündmuste nimekiri. Tagastab eventId, eventName, eventDescription, eventStartDate, eventEndDate, eventLocation, imageData, eventSeason",
            description = "Tagastab avalikud sündmused. Parameetriga season={ALL|KEVAD|SUVI|SÜGIS|TALV} saab filtreerida toimumisaja järgi. Sesoon arvutatakse start_date kuu põhjal. Autentimist ei nõuta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public List<EventListResponseDto> getEvents(@RequestParam(defaultValue = "Kõik") String season) {
        return eventService.getEvents(season);
    }
}
