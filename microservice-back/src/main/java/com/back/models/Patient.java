package com.back.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "First Nname is mandatory")
    @Size(min = 2, message = "2 characters min.")
    @Size(max = 50, message = "50 characters max.")
    private String firstName;
    @NotBlank(message = "Last Nname is mandatory")
    @Size(min = 2, message = "2 characters min.")
    @Size(max = 50, message = "50 characters max.")
    private String lastName;
    @NotNull(message = "Birthdate is mandatory")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;
    @NotBlank(message = "Gender is mandatory")
    private String gender;
    private String address;
    private String phoneNumber;

    public Patient(Integer id, String firstName, String lastName, LocalDate birthdate, String gender, String address, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Patient() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
