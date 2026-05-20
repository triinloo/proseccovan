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
    private String bookingPackageType;
    private String bookingAddress;
    private String latitude;
    private String longitude;
    private String info;
    private String bookingStatus;
}