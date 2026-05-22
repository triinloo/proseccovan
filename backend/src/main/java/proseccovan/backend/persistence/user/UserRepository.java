package proseccovan.backend.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {


    @Query("select u from User u where u.email = :email and u.password = :password")
    Optional<User> findUserBy(String email, String password);

    @Query("select (count(u) > 0) from User u where u.email = :email")
    boolean userExistsBy(String email);


}