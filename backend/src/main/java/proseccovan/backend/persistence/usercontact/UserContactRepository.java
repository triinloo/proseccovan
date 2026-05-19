package proseccovan.backend.persistence.usercontact;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserContactRepository extends JpaRepository<UserContact, Integer> {

    UserContact findByUser_Id(Integer userId);
}