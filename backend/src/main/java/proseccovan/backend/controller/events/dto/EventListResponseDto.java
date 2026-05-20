package proseccovan.backend.controller.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventListResponseDto {
    private Integer eventId;
    private String eventName;
    private String eventDescription;
    private String eventStartDate;
    private String eventEndDate;
    private String eventLocation;
    private String imageData;
    private String eventSeason;
}
