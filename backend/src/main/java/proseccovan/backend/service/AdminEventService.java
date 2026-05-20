package proseccovan.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import proseccovan.backend.controller.events.dto.EventListResponseDto;
import proseccovan.backend.infrastructure.exception.DataNotFoundException;
import proseccovan.backend.persistence.event.Event;
import proseccovan.backend.persistence.event.EventRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminEventService {

    private final EventRepository eventRepository;

    public List<EventListResponseDto> getAdminEvents() {
        return eventRepository.findAll().stream()
                .map(this::toEventListResponseDto)
                .toList();
    }

    public void deleteAdminEvent(Integer eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new DataNotFoundException("Sündmust ei leitud", 333));
        eventRepository.delete(event);
    }

    private EventListResponseDto toEventListResponseDto(Event event) {
        EventListResponseDto dto = new EventListResponseDto();
        dto.setEventId(event.getId());
        dto.setEventName(event.getName());
        dto.setEventDescription(event.getDescription());
        dto.setEventStartDate(event.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dto.setEventEndDate(event.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dto.setEventLocation(event.getLocation());
        dto.setImageData(event.getImageUrl());
        dto.setEventSeason(toSeason(event.getStartDate()));
        return dto;
    }

    private String toSeason(LocalDate date) {
        return switch (date.getMonthValue()) {
            case 3, 4, 5 -> "KEVAD";
            case 6, 7, 8 -> "SUVI";
            case 9, 10, 11 -> "SÜGIS";
            default -> "TALV";
        };
    }
}
