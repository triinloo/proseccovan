package proseccovan.backend.persistence.booking;

public class BookingStatusMapper {

    public static String toBookingStatus(String status) {
        return switch (status) {
            case "O" -> "OOTEL";
            case "K" -> "KINNITATUD";
            case "T" -> "TÜHISTATUD";
            default -> status;
        };
    }
}