package proseccovan.backend.persistence.booking;

import jakarta.persistence.*;
import lombok.Data;
import proseccovan.backend.persistence.bookingpackage.BookingPackage;
import proseccovan.backend.persistence.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "package_id")
    private BookingPackage bookingPackage;

    @Column(name = "address")
    private String address;

    @Column(name = "longitude")
    private BigDecimal longitude;

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "status")
    private String status;

    @Column(name = "booking_type_info")
    private String bookingTypeInfo;
}