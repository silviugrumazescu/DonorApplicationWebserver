package com.example.donationapp.model;
import com.example.donationapp.factory.NotificationChannel;
import com.example.donationapp.factory.NotificationSenderFactory;
import jakarta.persistence.*;

import java.util.Date;

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
    private NotificationChannel notificationChannel;

    public Appointment() {}

    public Appointment(Donor donor, Doctor doctor, BloodBank bloodBank, Date date, NotificationChannel notificationChannel) {
        this.donor = donor;
        this.doctor = doctor;
        this.bloodBank = bloodBank;
        this.date = date;
        this.isConfirmed = false;
        this.notificationChannel = notificationChannel;
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
    public NotificationChannel getNotificationType() {
        return notificationChannel;
    }
    public void setNotificationType(NotificationChannel notificationChannel) {
        this.notificationChannel = notificationChannel;
    }
}
