package com.example.donationapp.service;

import com.example.donationapp.dto.BloodbankPreview;
import com.example.donationapp.model.BloodBank;
import com.example.donationapp.model.District;
import com.example.donationapp.repository.BloodBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BloodbankService {

    @Autowired
    BloodBankRepository bloodBankRepository;

    public List<BloodBank> getBloodbanksByDistrict(District district) {
        List<BloodBank> bloodBanks = bloodBankRepository.findBloodBankByDistrict(district);

        return bloodBanks;
    }


}
