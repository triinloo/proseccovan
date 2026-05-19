package proseccovan.backend.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proseccovan.backend.controller.register.RegisterRequestDto;
import proseccovan.backend.infrastructure.exception.ForbiddenException;
import proseccovan.backend.infrastructure.exception.PrimaryKeyNotFoundException;
import proseccovan.backend.persistence.role.Role;
import proseccovan.backend.persistence.role.RoleRepository;
import proseccovan.backend.persistence.user.User;
import proseccovan.backend.persistence.user.UserRepository;
import proseccovan.backend.persistence.usercontact.UserContact;
import proseccovan.backend.persistence.usercontact.UserContactRepository;

import static proseccovan.backend.infrastructure.error.ErrorResponse.EMAIL_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
public class RegisterService {
    public static final int CUSTOMER_ROLE_ID = 2;
    private final UserRepository userRepository;
    private final UserContactRepository userContactRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public void registerNewCustomer(RegisterRequestDto registerRequestDto) {
        validateEmailIsAvailable(registerRequestDto.getEmail());
        Role role = roleRepository.findById(CUSTOMER_ROLE_ID)
                .orElseThrow(() -> new PrimaryKeyNotFoundException("roleId", CUSTOMER_ROLE_ID));

        User user = new User();
        user.setEmail(registerRequestDto.getEmail());
        user.setPassword(registerRequestDto.getPassword());
        user.setRole(role);
        User savedUser = userRepository.save(user);

        UserContact userContact = new UserContact();
        userContact.setUser(savedUser);
        userContact.setUserName(registerRequestDto.getCustomerName());
        userContact.setPhone("");
        userContactRepository.save(userContact);

    }

    private void validateEmailIsAvailable(String email) {
        boolean emailExists = userRepository.userExistsBy(email);
        if (emailExists) {
            throw new ForbiddenException(EMAIL_ALREADY_EXISTS.getMessage(), EMAIL_ALREADY_EXISTS.getErrorCode());
        }

    }
//    private void newUser createAndSaveUser(RegisterRequestDto registerRequestDto){
//        createUser(registerRequestDto);
//
//    }


}
