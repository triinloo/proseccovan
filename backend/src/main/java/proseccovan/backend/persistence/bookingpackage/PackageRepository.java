package proseccovan.backend.persistence.bookingpackage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PackageRepository extends JpaRepository<Package, Integer> {

    Optional<Package> findByName(String name);
}
