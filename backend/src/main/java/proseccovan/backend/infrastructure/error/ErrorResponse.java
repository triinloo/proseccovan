package proseccovan.backend.infrastructure.error;

import lombok.Getter;

@Getter
public enum ErrorResponse {
    INVALID_LOGIN("Vale email või parool", 111),
    NO_LOCATION_FOUND("Ei leitud ühtegi pangaautomaati", 222),
    LOCATION_NAME_UNAVAILABLE("Sellise nimega pangaautomaadi asukoht on juba süsteemis olemas", 333),
    ;

    private final String message;
    private final Integer errorCode;

    ErrorResponse(String message, Integer errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
