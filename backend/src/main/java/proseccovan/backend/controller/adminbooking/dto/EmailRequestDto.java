package proseccovan.backend.controller.adminbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequestDto {
    private String emailTitle;
    private String emailMessage;
}
