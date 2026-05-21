package proseccovan.backend.controller.register.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RegisterRequestDto {
    private String customerName;
    private String email;
    private String password;
}
