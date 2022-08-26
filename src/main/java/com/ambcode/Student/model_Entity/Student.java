package com.ambcode.Student.model_Entity;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class Student {

    private final UUID studentId;
    @NotBlank
    private final String firstname;
    @NotBlank
    private final String lastname;


    @NotBlank
    private final String email;
    @NotNull
    private final Gender gender;
    public enum Gender {
        MALE,FEMALE
    }

    public Student(@JsonProperty("studentId") UUID studentId, // this annotations are used to tell java to refer this
                   @JsonProperty("firstName") String firstname, // properties from json object as key value
                   @JsonProperty("lastName") String lastname, //i.e when we recieve data from clien @RequestBody
                   @JsonProperty("email") String email,
                   @JsonProperty("gender") Gender gender) {
        this.studentId = studentId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.gender = gender;
    }

    public UUID getStudentId() {
        return studentId;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public String getEmail() {
        return email;
    }
    public Gender getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                '}';
    }
}
