package com.example.donationapp.service;

import com.example.donationapp.dto.AppointmentDTO;
import com.example.donationapp.dto.AppointmentDonorPreviewDTO;
import com.example.donationapp.dto.DonorEditDTO;
import com.example.donationapp.factory.NotificationChannel;
import com.example.donationapp.factory.NotificationSenderFactory;
import com.example.donationapp.factory.NotificationType;
import com.example.donationapp.model.*;
import com.example.donationapp.repository.AppointmentRepository;
import com.example.donationapp.repository.BloodBankRepository;
import com.example.donationapp.repository.DoctorRepository;
import com.example.donationapp.repository.DonorRepository;
import com.example.donationapp.utils.DataParser;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DonorService {

    @Autowired
    DonorRepository donorRepository;

    public Donor getDonor(String email) {
        Donor d = donorRepository.findById(email).get();
        return d;
    }

    public void updateDonor(Donor donor) throws Exception{
        donorRepository.save(donor);
    }

}
