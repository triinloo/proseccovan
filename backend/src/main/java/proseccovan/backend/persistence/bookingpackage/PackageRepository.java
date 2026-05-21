package proseccovan.backend.persistence.bookingpackage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

import java.util.Optional;

public interface PackageRepository extends JpaRepository<Package, Integer> {

    @Query("select p from Package p where p.name = :name")
    Optional<Package> findPackageByType(String name);
    Optional<Package> findByName(String name);
    Optional<Package> findByName(String name);
}