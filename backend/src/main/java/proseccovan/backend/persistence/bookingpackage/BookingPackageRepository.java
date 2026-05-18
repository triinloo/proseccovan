package proseccovan.backend.persistence.bookingpackage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingPackageRepository extends JpaRepository<BookingPackage, Integer> {

    BookingPackage findByName(String name);
}