package proseccovan.backend.persistence.usercontact;

import jakarta.persistence.*;
import lombok.Data;
import proseccovan.backend.persistence.user.User;

@Data
@Entity
@Table(name = "user_contact")
public class UserContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "phone")
    private String phone;

    @Column(name = "user_name")
    private String userName;
}