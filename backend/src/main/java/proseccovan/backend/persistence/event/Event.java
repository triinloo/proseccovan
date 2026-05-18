package proseccovan.backend.persistence.event;

import jakarta.persistence.*;
import lombok.Data;
import proseccovan.backend.persistence.user.User;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private User createdByUser;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;
}