package com.example.thenext.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String Gender;
    @Column
    private Integer Age;
    @Column
    private String Occupation;
    @Column
    private String ZipCode;

    public User(Long id, String gender, Integer age, String occupation, String zipCode) {
        this.id = id;
        Gender = gender;
        Age = age;
        Occupation = occupation;
        ZipCode = zipCode;
    }

    public User() {

    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public Integer getAge() {
        return Age;
    }

    public void setAge(Integer age) {
        Age = age;
    }

    public String getOccupation() {
        return Occupation;
    }

    public void setOccupation(String occupation) {
        Occupation = occupation;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }


    public void setId(Long id) {
        this.id = id;
    }

    @jakarta.persistence.Id
    public Long getId() {
        return id;
    }
}
