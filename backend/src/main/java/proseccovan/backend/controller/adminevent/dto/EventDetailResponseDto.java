package proseccovan.backend.controller.adminevent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDetailResponseDto {
    private Integer eventId;
    private String eventName;
    private String eventStartDate;
    private String eventEndDate;
    private String eventLocation;
    private String eventDescription;
    private String imageData;
}
