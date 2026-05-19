package proseccovan.backend.controller.register;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import proseccovan.backend.infrastructure.error.ApiError;
import proseccovan.backend.service.RegisterService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @Operation(
            summary = "Uue kliendi registreerimine",
            description = "Loob uue kasutaja ja kontaktinfo. Kui antud e-post on juba registreeritud, visatakse viga errorCode'ga 222.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403",
                    description = "E-post on juba registreeritud",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping("/register")
    public void registerNewCustomer(@RequestBody RegisterRequestDto registerRequestDto) {
        registerService.registerNewCustomer(registerRequestDto);
    }




}
