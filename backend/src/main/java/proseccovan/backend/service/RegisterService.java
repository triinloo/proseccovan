package proseccovan.backend.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import proseccovan.backend.controller.register.RegisterRequestDto;
import proseccovan.backend.persistence.role.RoleRepository;
import proseccovan.backend.persistence.user.UserRepository;
import proseccovan.backend.persistence.usercontact.UserContactRepository;

@Service
@RequiredArgsConstructor

public class RegisterService {
    private final UserRepository userRepository;
    private final UserContactRepository userContactRepository;
    private final RoleRepository roleRepository;

    public void register(RegisterRequestDto request){
        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new DataNotFoundException();
        }
    }


//    public void RegisterRequestDto register(String customerName, String email, String password) {
//        user user = userRepository.findUserBy(customerName, email, password)
//                .orElseThrow(() -> new)
//
//        return null;
//    }
}
