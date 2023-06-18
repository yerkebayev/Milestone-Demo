package unist.ep.milestone2.repository;

public class ClubRequest {
    private Long id;
    private String name;
    private Long clubType;
    private String email;
    private String mission;
    private String description;
    private String headEmail;
    private String contact;

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

    public Long getClubType() {
        return clubType;
    }

    public void setClubType(Long clubType) {
        this.clubType = clubType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeadEmail() {
        return headEmail;
    }

    public void setHeadEmail(String headEmail) {
        this.headEmail = headEmail;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public ClubRequest() {
    }


    public ClubRequest(Long id, String name, Long clubType, String email, String mission, String description, String headEmail, String contact) {
        this.id = id;
        this.name = name;
        this.clubType = clubType;
        this.email = email;
        this.mission = mission;
        this.description = description;
        this.headEmail = headEmail;
        this.contact = contact;
    }

    public ClubRequest(String name, Long clubType, String email, String mission, String description, String headEmail, String contact) {
        this.name = name;
        this.clubType = clubType;
        this.email = email;
        this.mission = mission;
        this.description = description;
        this.headEmail = headEmail;
        this.contact = contact;
    }
}
