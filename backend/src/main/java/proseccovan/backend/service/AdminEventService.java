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
public class AdminEventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventsService eventsService;

    public List<EventListResponseDto> getAdminEvents() {
        return eventsService.getEvents("Kõik");
    }

    public EventDetailResponseDto getEventById(Integer eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new DataNotFoundException(DATA_NOT_FOUND.getMessage(), DATA_NOT_FOUND.getErrorCode()));
        return toEventDetailResponseDto(event);
    }

    public EventDetailResponseDto createEvent(EventRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new DataNotFoundException(DATA_NOT_FOUND.getMessage(), DATA_NOT_FOUND.getErrorCode()));

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
                .orElseThrow(() -> new DataNotFoundException(DATA_NOT_FOUND.getMessage(), DATA_NOT_FOUND.getErrorCode()));

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
                .orElseThrow(() -> new DataNotFoundException(DATA_NOT_FOUND.getMessage(), DATA_NOT_FOUND.getErrorCode()));
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
}
