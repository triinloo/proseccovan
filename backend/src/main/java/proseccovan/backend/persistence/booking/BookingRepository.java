package proseccovan.backend.persistence.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query("select b from Booking b where b.user.id = :userId")
    List<Booking> findBookingsBy(Integer userId);

    @Query("select b from Booking b where (:status = 'A') or (b.status = :status)")
    List<Booking> findBookingsBy(String status);
}