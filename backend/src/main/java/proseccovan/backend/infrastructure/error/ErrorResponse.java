package proseccovan.backend.infrastructure.error;

import lombok.Getter;

@Getter
public enum ErrorResponse {
    INVALID_LOGIN("Vale email või parool", 111),
    EMAIL_ALREADY_EXISTS("Selline email on juba varasemalt registreeritud", 222),
    DATA_NOT_FOUND("Andmeid ei leitud", 333),
    CANCELLATION_NOT_ALLOWED("Broneeringut ei saa tühistada",444),
    ;

    private final String message;
    private final Integer errorCode;

    ErrorResponse(String message, Integer errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
