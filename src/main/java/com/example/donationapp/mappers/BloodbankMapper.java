package com.example.donationapp.mappers;

import com.example.donationapp.dto.BloodbankPreview;
import com.example.donationapp.model.BloodBank;
import com.example.donationapp.model.District;
import com.example.donationapp.service.BloodbankService;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Service
public class BloodbankMapper {

    public ArrayList<BloodbankPreview> mapBloodbanksToPreview(List<BloodBank> bloodBanks) {
        ArrayList<BloodbankPreview> bloodbankPreviews = new ArrayList<>();
        bloodBanks.forEach(b -> {bloodbankPreviews.add(new BloodbankPreview(
                b.getId(),
                b.getName(),
                b.getDistrict()
        ));});
        return bloodbankPreviews;
    }
}
