package proseccovan.backend.controller.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingOverviewDto {
    private Integer id;
    private String bookingId;
    private String customerName;
    private String bookingDate;
    private String bookingType;
    private String location;
    private String packageType;
    private String bookingStatus;
}