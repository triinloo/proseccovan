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

@Service
@RequiredArgsConstructor
public class AdminEventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public List<EventListResponseDto> getAdminEvents() {
        return eventRepository.findAll().stream()
                .map(this::toEventListResponseDto)
                .toList();
    }

    public EventDetailResponseDto getEventById(Integer eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new DataNotFoundException("Sündmust ei leitud", 333));
        return toEventDetailResponseDto(event);
    }

    public EventDetailResponseDto createEvent(EventRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Kasutajat ei leitud", 333));

        Event event = new Event();
        event.setCreatedByUser(user);
        event.setName(dto.getEventName());
        event.setLocation(dto.getEventLocation());
        event.setStartDate(LocalDate.parse(dto.getEventStartDate()));
        event.setEndDate(LocalDate.parse(dto.getEventEndDate()));
        event.setDescription(dto.getEventDescription());
        event.setImageUrl(dto.getImageData());

        Event saved = eventRepository.save(event);
        return toEventDetailResponseDto(saved);
    }

    public EventDetailResponseDto updateEvent(Integer eventId, EventRequestDto dto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new DataNotFoundException("Sündmust ei leitud", 333));

        event.setName(dto.getEventName());
        event.setLocation(dto.getEventLocation());
        event.setStartDate(LocalDate.parse(dto.getEventStartDate()));
        event.setEndDate(LocalDate.parse(dto.getEventEndDate()));
        event.setDescription(dto.getEventDescription());
        event.setImageUrl(dto.getImageData());

        Event saved = eventRepository.save(event);
        return toEventDetailResponseDto(saved);
    }

    public void deleteAdminEvent(Integer eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new DataNotFoundException("Sündmust ei leitud", 333));
        eventRepository.delete(event);
    }

    private EventDetailResponseDto toEventDetailResponseDto(Event event) {
        EventDetailResponseDto dto = new EventDetailResponseDto();
        dto.setEventId(event.getId());
        dto.setEventName(event.getName());
        dto.setEventStartDate(event.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dto.setEventEndDate(event.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dto.setEventLocation(event.getLocation());
        dto.setEventDescription(event.getDescription());
        dto.setImageData(event.getImageUrl());
        return dto;
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
