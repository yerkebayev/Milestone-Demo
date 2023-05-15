package unist.ep.milestone2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clubs")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private Long clubType_id;
    @Column
    private String description;
    @Column
    private String mission;
    @Column
    private String contact;
    @Column
    private Long head_id;

    public Club() {
    }

    public Club(Long id, String name, String email, Long clubType_id, String description, String mission, String contact, Long head_id) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.clubType_id = clubType_id;
        this.description = description;
        this.mission = mission;
        this.contact = contact;
        this.head_id = head_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getClubType_id() {
        return clubType_id;
    }

    public void setClubType_id(Long clubType_id) {
        this.clubType_id = clubType_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Long getHead_id() {
        return head_id;
    }

    public void setHead_id(Long head_id) {
        this.head_id = head_id;
    }
}
