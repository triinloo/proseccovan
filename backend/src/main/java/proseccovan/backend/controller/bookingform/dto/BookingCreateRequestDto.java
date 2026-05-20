package proseccovan.backend.controller.bookingform.dto;

import lombok.Data;

@Data
public class BookingCreateRequestDto {
    private String customerName;
    private String email;
    private String phoneNumber;
    private String bookingType;
    private String bookingDate;
    private String address;
    private String latitude;
    private String longitude;
    private String packageType;
    private String bookingInfo;
}
