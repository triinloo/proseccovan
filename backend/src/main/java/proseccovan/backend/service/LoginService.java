package proseccovan.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import proseccovan.backend.controller.login.dto.LoginRequestDto;
import proseccovan.backend.controller.login.dto.LoginResponseDto;
import proseccovan.backend.infrastructure.error.ErrorResponse;
import proseccovan.backend.infrastructure.exception.ForbiddenException;
import proseccovan.backend.persistence.user.User;
import proseccovan.backend.persistence.user.UserMapper;
import proseccovan.backend.persistence.user.UserRepository;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findUserBy(loginRequestDto.getEmail(), loginRequestDto.getPassword())
                .orElseThrow(() -> new ForbiddenException(ErrorResponse.INVALID_LOGIN.getMessage(), ErrorResponse.INVALID_LOGIN.getErrorCode()));
        return userMapper.toLoginResponseDto(user);
    }
}
