package proseccovan.backend.controller.register;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import proseccovan.backend.service.RegisterService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor

public class RegisterController {

    private final RegisterService registerService;

    @PostMapping("/register")

    public RegisterRequestDto register(@RequestParam String customerName, @RequestParam String email,@RequestParam String password){
        return registerService.register(customerName, email, password);
    }




}
