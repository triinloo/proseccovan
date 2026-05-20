package proseccovan.backend.controller.customerbooking.dto;

import lombok.*;



@Data
@NoArgsConstructor
@AllArgsConstructor

public class BookingResponseDto {
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