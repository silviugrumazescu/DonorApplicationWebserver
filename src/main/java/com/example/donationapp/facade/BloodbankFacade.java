package com.example.donationapp.facade;

import com.example.donationapp.dto.BloodbankPreview;
import com.example.donationapp.mappers.BloodbankMapper;
import com.example.donationapp.model.BloodBank;
import com.example.donationapp.model.District;
import com.example.donationapp.service.BloodbankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BloodbankFacade {

    @Autowired
    private BloodbankService bloodbankService;
    @Autowired
    private BloodbankMapper bloodbankMapper;

    public ArrayList<BloodbankPreview> getBloodbanksByDistrict(District district) {
        List<BloodBank> bloodBanks = bloodbankService.getBloodbanksByDistrict(district);
        return bloodbankMapper.mapBloodbanksToPreview(bloodBanks);
    }
}
