package unist.ep.milestone2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_club_types")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class UserClubType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long user_id;

    @Column
    private Long clubType_id;

    public UserClubType(Long user_id, Long clubType_id) {
        this.user_id = user_id;
        this.clubType_id = clubType_id;
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

    public Long getClubType_id() {
        return clubType_id;
    }

    public void setClubType_id(Long clubType_id) {
        this.clubType_id = clubType_id;
    }

    public UserClubType() {
    }

    public UserClubType(Long id, Long user_id, Long clubType_id) {
        this.id = id;
        this.user_id = user_id;
        this.clubType_id = clubType_id;
    }
}

