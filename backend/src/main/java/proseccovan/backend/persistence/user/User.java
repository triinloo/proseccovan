package proseccovan.backend.persistence.user;

import jakarta.persistence.*;
import lombok.Data;
import proseccovan.backend.persistence.role.Role;

@Data
@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
}