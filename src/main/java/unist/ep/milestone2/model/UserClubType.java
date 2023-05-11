package unist.ep.milestone2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_clubTypes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserClubType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long user_id;

    @Column
    private Integer clubType_id;

    public UserClubType(Long user_id, Integer clubType_id) {
        this.user_id = user_id;
        this.clubType_id = clubType_id;
    }
}

