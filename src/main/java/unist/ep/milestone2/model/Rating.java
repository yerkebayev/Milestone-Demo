package unist.ep.milestone2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ratings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Club club;

    @Column
    private Integer value;

    @Column
    private String comment;

    public Rating(User user, Club club, Integer value, String comment) {
        this.user = user;
        this.club = club;
        this.value = value;
        this.comment = comment;
    }
}
