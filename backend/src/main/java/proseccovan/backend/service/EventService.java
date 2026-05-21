package proseccovan.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import proseccovan.backend.controller.adminevent.dto.EventDetailResponseDto;
import proseccovan.backend.controller.adminevent.dto.EventRequestDto;
import proseccovan.backend.controller.events.dto.EventListResponseDto;
import proseccovan.backend.infrastructure.exception.DataNotFoundException;
import proseccovan.backend.persistence.event.Event;
import proseccovan.backend.persistence.event.EventRepository;
import proseccovan.backend.persistence.user.User;
import proseccovan.backend.persistence.user.UserRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static proseccovan.backend.infrastructure.error.ErrorResponse.DATA_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class EventService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public List<EventListResponseDto> getEvents(String season) {
        return eventRepository.findAll().stream()
                .filter(event -> season.equals("Kõik") ||toSeason(event.getStartDate()).equals(season))
                .map(this::toEventListResponseDto)
                .toList();
    }

    public EventDetailResponseDto getEventById(Integer eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(this::notFound);
        return toEventDetailResponseDto(event);
    }

    public EventDetailResponseDto createEvent(EventRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(this::notFound);

        Event event = new Event();
        event.setCreatedByUser(user);
        event.setName(dto.getEventName());
        event.setLocation(dto.getEventLocation());
        event.setStartDate(LocalDate.parse(dto.getEventStartDate()));
        event.setEndDate(LocalDate.parse(dto.getEventEndDate()));
        event.setDescription(dto.getEventDescription());
        event.setImageUrl(dto.getImageData());

        return toEventDetailResponseDto(eventRepository.save(event));
    }

    public EventDetailResponseDto updateEvent(Integer eventId, EventRequestDto dto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(this::notFound);

        event.setName(dto.getEventName());
        event.setLocation(dto.getEventLocation());
        event.setStartDate(LocalDate.parse(dto.getEventStartDate()));
        event.setEndDate(LocalDate.parse(dto.getEventEndDate()));
        event.setDescription(dto.getEventDescription());
        event.setImageUrl(dto.getImageData());

        return toEventDetailResponseDto(eventRepository.save(event));
    }

    public void deleteEvent(Integer eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(this::notFound);
        eventRepository.delete(event);
    }

    private EventListResponseDto toEventListResponseDto(Event event) {
        EventListResponseDto dto = new EventListResponseDto();
        dto.setEventId(event.getId());
        dto.setEventName(event.getName());
        dto.setEventDescription(event.getDescription());
        dto.setEventStartDate(event.getStartDate().format(DATE_FORMATTER));
        dto.setEventEndDate(event.getEndDate().format(DATE_FORMATTER));
        dto.setEventLocation(event.getLocation());
        dto.setImageData(event.getImageUrl());
        dto.setEventSeason(toSeason(event.getStartDate()));
        return dto;
    }

    private EventDetailResponseDto toEventDetailResponseDto(Event event) {
        EventDetailResponseDto dto = new EventDetailResponseDto();
        dto.setEventId(event.getId());
        dto.setEventName(event.getName());
        dto.setEventStartDate(event.getStartDate().format(DATE_FORMATTER));
        dto.setEventEndDate(event.getEndDate().format(DATE_FORMATTER));
        dto.setEventLocation(event.getLocation());
        dto.setEventDescription(event.getDescription());
        dto.setImageData(event.getImageUrl());
        return dto;
    }

    private String toSeason(LocalDate date) {
        return switch (date.getMonthValue()) {
            case 3, 4, 5 -> "KEVAD";
            case 6, 7, 8 -> "SUVI";
            case 9, 10, 11 -> "SÜGIS";
            case 1, 2, 12 -> "TALV";
            default -> "Kõik";
        };
    }

    private DataNotFoundException notFound() {
        return new DataNotFoundException(DATA_NOT_FOUND.getMessage(), DATA_NOT_FOUND.getErrorCode());
    }
}