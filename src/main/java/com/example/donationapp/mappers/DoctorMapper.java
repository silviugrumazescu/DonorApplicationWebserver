package com.example.donationapp.mappers;

import com.example.donationapp.dto.DoctorPreview;
import com.example.donationapp.model.District;
import com.example.donationapp.model.Doctor;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorMapper {

    public List<DoctorPreview> mapDoctorsForAdmin(List<Doctor> doctors) {
        List<DoctorPreview> doctorPreviews = new ArrayList<>();
        doctors.forEach( (d) -> {
            doctorPreviews.add(new DoctorPreview(
                    d.getEmail(),
                    d.getName(),
                    d.getDistrict().name()));
        });

        return doctorPreviews;
    }

    public List<DoctorPreview> mapDoctorsToPreview(List<Doctor> doctors) {

        ArrayList<DoctorPreview> doctorPreviews = new ArrayList<DoctorPreview>();
        doctors.forEach(doctor -> {doctorPreviews.add(new DoctorPreview(
                doctor.getEmail(),
                doctor.getName(),
                doctor.getDistrict().name()
        ));});
        return doctorPreviews;
    }


}
