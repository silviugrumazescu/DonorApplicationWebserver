package com.example.donationapp.mappers;

import com.example.donationapp.dto.AppointmentDoctorPreviewDTO;
import com.example.donationapp.dto.DoctorAppointmentsPageDTO;
import com.example.donationapp.model.Appointment;
import com.example.donationapp.utils.DataParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentMapper {

    @Autowired DataParser dataParser;
    public List<AppointmentDoctorPreviewDTO> mapAppointmentsForDoctor(List<Appointment> appointments) {
        List<AppointmentDoctorPreviewDTO> appointmentDoctorPreviewDTOS = new ArrayList<>();

        appointments.forEach(appointment -> {
            appointmentDoctorPreviewDTOS.add(new AppointmentDoctorPreviewDTO(
                    appointment.getId(),
                    appointment.getDonor().getName(),
                    appointment.getDate(),
                    dataParser.formatDate(appointment.getDate()),
                    appointment.getConfirmed()
            ));
        });
        return appointmentDoctorPreviewDTOS;
    }

    public DoctorAppointmentsPageDTO mapAppointmentsForDoctorPage(Page<Appointment> appointmentsPage) {
        DoctorAppointmentsPageDTO doctorAppointmentsPageDTO = new DoctorAppointmentsPageDTO(
                mapAppointmentsForDoctor(appointmentsPage.getContent()),
                (int)appointmentsPage.getTotalElements()
        );

        return doctorAppointmentsPageDTO;
    }

}
