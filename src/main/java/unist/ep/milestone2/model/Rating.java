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

    @Column
    private Long user_id;

    @Column
    private Long club_id;

    @Column
    private Integer value;

    @Column
    private String comment;

    public Rating(Long user_id, Long club_id, Integer value, String comment) {
        this.user_id = user_id;
        this.club_id = club_id;
        this.value = value;
        this.comment = comment;
    }
}
