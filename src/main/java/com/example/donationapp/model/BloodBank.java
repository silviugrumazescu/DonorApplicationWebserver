package com.example.donationapp.model;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import javax.print.Doc;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class BloodBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private District district;
    private Boolean isFunctional;
    private String address;
    private Time programStart;
    private Time programEnd;
    private String openDays = "L-V";
    @OneToMany(mappedBy = "bloodBank1")
    private List<Doctor> doctors;
    @OneToMany(mappedBy = "bloodBank")
    private List<Appointment> appointments;

    public BloodBank(){}

    public BloodBank(String name, District district, Boolean isFunctional, String address) {
        this.name = name;
        this.district = district;
        this.isFunctional = isFunctional;
        this.address = address;
        this.doctors = new ArrayList<Doctor>();
        this.appointments = new ArrayList<Appointment>();
    }

    public Integer getId() {
        return id;
    }
    public District getDistrict() {
        return district;
    }
    public void setDistrict(District district) {
        this.district = district;
    }
    public String getName() {
        return name;
    }
    public Time getProgramStart() {
        return programStart;
    }
    public void setProgramStart(Time programStart) {
        this.programStart = programStart;
    }
    public Time getProgramEnd() {
        return programEnd;
    }
    public void setProgramEnd(Time programEnd) {
        this.programEnd = programEnd;
    }
    public String getOpenDays() {
        return openDays;
    }
    public void setOpenDays(String openDays) {
        this.openDays = openDays;
    }
    public List<Appointment> getAppointments() {
        return appointments;
    }
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Boolean getFunctional() {
        return isFunctional;
    }
    public void setFunctional(Boolean functional) {
        isFunctional = functional;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public List<Doctor> getDoctors() {
        return doctors;
    }
    public void setDoctors(ArrayList<Doctor> doctors) {
        this.doctors = doctors;
    }

}
