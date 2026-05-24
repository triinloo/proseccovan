package proseccovan.backend.persistence.booking;

public class BookingStatusMapper {

    public static String toBookingStatus(String abbrev) {
        return BookingStatus.fromAbbrev(abbrev).getStatusText();
    }
}