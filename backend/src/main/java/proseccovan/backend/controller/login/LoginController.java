package proseccovan.backend.controller.login;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proseccovan.backend.service.LoginService;

@RestController
@RequestMapping("/api")
public class LoginController {

    private LoginService loginService;



}
