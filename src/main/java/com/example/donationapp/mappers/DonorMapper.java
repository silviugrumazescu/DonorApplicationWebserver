package com.example.donationapp.mappers;

import com.example.donationapp.dto.DonorEditDTO;
import com.example.donationapp.model.Donor;
import com.example.donationapp.model.Role;
import com.example.donationapp.utils.DataParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DonorMapper {

    @Autowired
    DataParser dataParser;

    public DonorEditDTO mapDonorToDTO(Donor donor) {
        DonorEditDTO d = new DonorEditDTO(
                donor.getEmail(),
                donor.getCNP(),
                donor.getName(),
                "",
                donor.getDistrict().name(),
                donor.getBloodType().name()
        );
        return d;
    }

    public Donor mapDTOtoDonor(DonorEditDTO donorEditDTO) throws DataParser.InvalidInputException {
        Donor donor;
        try {
            donor = new Donor(
                    donorEditDTO.name,
                    donorEditDTO.email,
                    "",
                    dataParser.parseDistrict(donorEditDTO.district),
                    Role.Donor,
                    dataParser.parseBloodType(donorEditDTO.bloodType),
                    donorEditDTO.CNP,
                    ""
            );
            return donor;
        } catch (Exception ex) {
            throw ex;
        }
    }

}
