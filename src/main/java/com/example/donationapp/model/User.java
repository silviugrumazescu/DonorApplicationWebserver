package com.example.donationapp.model;

import jakarta.persistence.*;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    protected String email;
    protected String CNP;
    protected String name;
    protected String password;
    protected District district;
    protected Role role;
    protected String phoneNumber;

    public User(){}

    public User(String name, String email, String password, District district, Role role, String CNP, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.district = district;
        this.role = role;
        this.CNP = CNP;
        this.phoneNumber = phoneNumber;
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
    public String getCNP() {
        return CNP;
    }
    public void setCNP(String CNP) {
        this.CNP = CNP;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public District getDistrict() {
        return district;
    }
    public void setDistrict(District district) {
        this.district = district;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
