package proseccovan.backend.controller.customerbookings.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BookingSummaryDto {
    private String bookingId;
    private String customerName;
    private String bookingDate;
    private String bookingType;
    private String location;
    private String packageType;
    private String bookingStatus;
}