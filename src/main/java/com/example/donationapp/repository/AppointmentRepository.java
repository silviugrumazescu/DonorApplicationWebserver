package com.example.donationapp.repository;

import com.example.donationapp.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query(value = "SELECT ap FROM Appointment ap WHERE ap.doctor.email = :email",
            countQuery = "SELECT count(ap) FROM Appointment ap WHERE ap.doctor.email = :email")
    Page<Appointment> findAllByDoctorEmail(String email, Pageable pageable);
}
