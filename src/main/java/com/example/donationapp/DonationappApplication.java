package com.example.donationapp;

import com.example.donationapp.model.*;
import com.example.donationapp.repository.BloodBankRepository;
import com.example.donationapp.repository.DoctorRepository;
import com.example.donationapp.repository.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DonationappApplication {

	static DonorRepository donorRepository;
	static DoctorRepository doctorRepository;
	@Autowired
	static BloodBankRepository bloodBankRepository;

	public static void main(String[] args) {
		SpringApplication.run(DonationappApplication.class, args);
	}

}
