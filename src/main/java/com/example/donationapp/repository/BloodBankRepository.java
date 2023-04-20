package com.example.donationapp.repository;

import com.example.donationapp.model.BloodBank;
import com.example.donationapp.model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodBankRepository extends JpaRepository<BloodBank, Integer> {

    List<BloodBank> findBloodBankByDistrict(District district);

}
