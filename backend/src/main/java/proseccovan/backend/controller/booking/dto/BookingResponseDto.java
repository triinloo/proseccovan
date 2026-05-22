package proseccovan.backend.controller.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDto {
    private String bookingId;
    private String customerName;
    private String email;
    private String phoneNumber;
    private String bookingDate;
    private String bookingType;
    private String packageType;
    private String address;
    private String latitude;
    private String longitude;
    private String bookingInfo;
    private String bookingStatus;
}