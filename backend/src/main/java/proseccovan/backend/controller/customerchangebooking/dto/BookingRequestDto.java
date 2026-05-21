package proseccovan.backend.controller.customerchangebooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDto {
    private String customerName;
    private String email;
    private String phoneNumber;
    private String bookingDate;
    private String bookingType;
    private String address;
    private String latitude;
    private String longitude;
    private String packageType;
    private String bookingInfo;
}
