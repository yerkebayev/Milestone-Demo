package unist.ep.milestone2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ratings")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
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

    public Rating(Long id, Long user_id, Long club_id, Integer value, String comment) {
        this.id = id;
        this.user_id = user_id;
        this.club_id = club_id;
        this.value = value;
        this.comment = comment;
    }

    public Rating() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getClub_id() {
        return club_id;
    }

    public void setClub_id(Long club_id) {
        this.club_id = club_id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
