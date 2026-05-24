package proseccovan.backend.persistence.booking;

import lombok.Getter;

@Getter
public enum BookingStatus {
    OOTEL("O", "OOTEL"),
    KINNITATUD("K", "KINNITATUD"),
    TUHISTATUD("T", "TÜHISTATUD");

    private final String abbrev;
    private final String statusText;

    BookingStatus(String abbrev, String statusText) {
        this.abbrev = abbrev;
        this.statusText = statusText;
    }

    public static BookingStatus fromAbbrev(String abbrev) {
        for (BookingStatus status : values()) {
            if (status.abbrev.equals(abbrev)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Tundmatu broneeringu staatus: " + abbrev);
    }
}