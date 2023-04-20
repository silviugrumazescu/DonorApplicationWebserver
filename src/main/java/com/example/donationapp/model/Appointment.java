package com.example.donationapp.model;
import com.mysql.cj.x.protobuf.MysqlxExpr;
import jakarta.persistence.*;

import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "donor_email")
    private Donor donor;
    @ManyToOne
    @JoinColumn(name = "doctor_email")
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "bloodBank_id")
    private BloodBank bloodBank;
    private Date date;
    private Boolean isConfirmed;

    public Appointment() {}

    public Appointment(Donor donor, Doctor doctor, BloodBank bloodBank, Date date) {
        this.donor = donor;
        this.doctor = doctor;
        this.bloodBank = bloodBank;
        this.date = date;
        this.isConfirmed = false;
    }

    public BloodBank getBloodBank() {
        return bloodBank;
    }
    public void setBloodBank(BloodBank bloodBank) {
        this.bloodBank = bloodBank;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Donor getDonor() {
        return donor;
    }
    public void setDonor(Donor donor) {
        this.donor = donor;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Doctor getDoctor() {
        return doctor;
    }
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    public Boolean getConfirmed() {
        return isConfirmed;
    }
    public void setConfirmed(Boolean confirmed) {
        isConfirmed = confirmed;
    }
}
