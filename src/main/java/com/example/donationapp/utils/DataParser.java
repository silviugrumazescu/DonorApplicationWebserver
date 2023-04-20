package com.example.donationapp.utils;

import com.example.donationapp.model.BloodType;
import com.example.donationapp.model.District;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Component
public class DataParser {

    public District parseDistrict(String district) throws InvalidInputException{
        for(District d : District.values()) {
            if(d.name().equals(district)) {
                return d;
            }
        }

        throw new InvalidInputException("Invalid district");
    }

    public BloodType parseBloodType(String bloodType) throws InvalidInputException {
        for(BloodType b : BloodType.values()) {
            if(b.name().equals(bloodType)) {
                return b;
            }
        }
        throw new InvalidInputException("Invalid bloodType");
    }

    public String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        return formatter.format(date);
    }


    public class InvalidInputException extends Exception {
        public InvalidInputException(String errorMessage) {
            super(errorMessage);
        }
    }


}
