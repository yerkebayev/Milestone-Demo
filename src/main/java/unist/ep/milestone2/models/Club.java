package unist.ep.milestone2.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "clubs")
@Data
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private Long typeId;
    @Column
    private String description;
    @Column
    private String mission;
    @Column
    private Long headId;
    @Column
    private String contact;

    public Club() {
    }

    public Club(Long id, String name, Long typeId, String description, String mission, Long headId, String contact) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.description = description;
        this.mission = mission;
        this.headId = headId;
        this.contact = contact;
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

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
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

    public Long getHeadId() {
        return headId;
    }

    public void setHeadId(Long headId) {
        this.headId = headId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
