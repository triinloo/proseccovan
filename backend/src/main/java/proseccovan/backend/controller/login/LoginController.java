package proseccovan.backend.controller.login;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proseccovan.backend.controller.login.dto.LoginRequestDto;
import proseccovan.backend.controller.login.dto.LoginResponseDto;
import proseccovan.backend.infrastructure.error.ApiError;
import proseccovan.backend.service.LoginService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;


    @PostMapping("/login")
    @Operation(summary = "Sisse logimine. Tagastab userId ja roleName",
            description = """
                    Süsteemist otsitakse email ja password abil kasutajat.
                    Kui vastet ei leita visatakse viga errorCode'ga 111""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403",
                    description = "Vale email või parool",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))})
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return loginService.login(loginRequestDto);
    }

}
