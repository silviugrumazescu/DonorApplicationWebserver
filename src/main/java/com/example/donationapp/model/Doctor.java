package com.example.donationapp.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Doctor extends User{

    @ManyToOne
    @JoinColumn(name = "bloodBank1_id")
    private BloodBank bloodBank1;

    @OneToMany(mappedBy = "doctor", cascade=CascadeType.REMOVE, orphanRemoval = true)
    private List<Appointment> appointments;

    public Doctor(){}

    public Doctor(String name, String email, String password, District district, Role role, BloodBank bloodBank, String CNP) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.district = district;
        this.role = role;
        this.bloodBank1 = bloodBank;
        this.CNP = CNP;

        appointments = new ArrayList<Appointment>();
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
    public BloodBank getBloodBank1() {
        return bloodBank1;
    }
    public void setBloodBank1(BloodBank bloodBank) {
        this.bloodBank1 = bloodBank;
    }

}
