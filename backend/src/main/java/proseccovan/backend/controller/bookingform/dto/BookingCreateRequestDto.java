package proseccovan.backend.controller.bookingform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingCreateRequestDto {

    private Integer userId;
    private String customerName;
    private String email;
    private String phoneNumber;
    private String bookingType;
    private LocalDate bookingDate;
    private String address;
    private String latitude;
    private String longitude;
    private String packageType;
    private String bookingInfo;

}


