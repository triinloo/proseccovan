package proseccovan.backend.controller.adminbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminBookingResponseDto {
    private String bookingId;
    private String customerName;
    private String email;
    private String phoneNumber;
    private String bookingDate;
    private String bookingType;
    private String bookingPackageType;
    private String bookingAddress;
    private String latitude;
    private String longitude;
    private String info;
    private String bookingStatus;
}
