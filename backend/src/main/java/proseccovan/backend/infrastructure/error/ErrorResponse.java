package proseccovan.backend.infrastructure.error;

import lombok.Getter;

@Getter
public enum ErrorResponse {
    INVALID_LOGIN("Vale email või parool", 111),
    EMAIL_ALREADY_EXISTS	("Selline email on juba varasemalt registreeritud", 222),
    ;

    private final String message;
    private final Integer errorCode;

    ErrorResponse(String message, Integer errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
