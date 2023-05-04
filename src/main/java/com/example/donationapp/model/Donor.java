package com.example.donationapp.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Donor extends User{

    private BloodType bloodType;

    @OneToMany(mappedBy="donor")
    public List<Appointment> appointments;

    public Donor(){}

    public Donor(String name, String email, String password, District district, Role role, BloodType bloodType, String CNP, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.district = district;
        this.role = role;
        this.appointments = new ArrayList<Appointment>();
        this.bloodType = bloodType;
        this.CNP = CNP;
        this.phoneNumber = phoneNumber;
    }

    public BloodType getBloodType() {
        return bloodType;
    }
    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }
    public List<Appointment> getAppointments() {
        return appointments;
    }
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

}