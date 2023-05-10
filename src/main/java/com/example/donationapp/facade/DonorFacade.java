package com.example.donationapp.facade;

import com.example.donationapp.dto.DonorEditDTO;
import com.example.donationapp.mappers.DonorMapper;
import com.example.donationapp.model.Donor;
import com.example.donationapp.model.Role;
import com.example.donationapp.repository.DonorRepository;
import com.example.donationapp.service.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

@Service
public class DonorFacade {

    @Autowired
    DonorService donorService;
    @Autowired
    DonorMapper donorMapper;

    public DonorEditDTO getDonor(String email) {
        Donor d = donorService.getDonor(email);
        return donorMapper.mapDonorToDTO(d);
    }

    public void updateDonor(DonorEditDTO donorEditDTO) throws Exception{
        Donor donor = donorService.getDonor(donorEditDTO.email);
        Donor updatedDonor;
        try {
            updatedDonor = donorMapper.mapDTOtoDonor(donorEditDTO);
            updatedDonor.setPassword(donor.getPassword());
            updatedDonor.setPhoneNumber(donor.getPhoneNumber());

            donorService.updateDonor(updatedDonor);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
