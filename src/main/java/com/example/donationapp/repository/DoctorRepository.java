package com.example.donationapp.repository;

import com.example.donationapp.model.District;
import com.example.donationapp.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {

    List<Doctor> findDoctorByDistrict(District district);

}
