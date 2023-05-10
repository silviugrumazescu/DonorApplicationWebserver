package com.example.donationapp.facade;

import com.example.donationapp.dto.AppointmentDTO;
import com.example.donationapp.dto.AppointmentDoctorPreviewDTO;
import com.example.donationapp.dto.AppointmentDonorPreviewDTO;
import com.example.donationapp.dto.DoctorAppointmentsPageDTO;
import com.example.donationapp.factory.NotificationChannel;
import com.example.donationapp.factory.NotificationSenderFactory;
import com.example.donationapp.factory.NotificationType;
import com.example.donationapp.mappers.AppointmentMapper;
import com.example.donationapp.model.Appointment;
import com.example.donationapp.model.BloodBank;
import com.example.donationapp.model.Doctor;
import com.example.donationapp.model.Donor;
import com.example.donationapp.service.AppointmentService;
import com.example.donationapp.service.DonorService;
import com.example.donationapp.service.NotificationService;
import com.example.donationapp.utils.DataParser;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class AppointmentFacade {

    @Autowired
    DataParser dataParser;
    @Autowired
    AppointmentService appointmentService;
    @Autowired
    AppointmentMapper appointmentMapper;

    public void createAppointment(AppointmentDTO appointmentDTO) throws Exception{
        Date date = dataParser.parseDate(appointmentDTO.date);

        Appointment appointment = appointmentService.createAppointment(
                appointmentDTO.donorEmail,
                date,
                appointmentDTO.bloodbankId,
                dataParser.parseNotificationChannel(appointmentDTO.notifyType)
        );

        NotificationService notificationService = NotificationSenderFactory.getNotificationService(NotificationChannel.EMAIL_NOTIFICATION);
        notificationService.send(NotificationType.CONFIRM, appointment);
    }

    public List<AppointmentDonorPreviewDTO> getAppointmentsByDonor(String email) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDonor(email);
        return appointmentMapper.mapAppointmentsForDonor(appointments);
    }

    public List<AppointmentDoctorPreviewDTO> getAppointmentsByDoctor(String email) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(email);
        return appointmentMapper.mapAppointmentsForDoctor(appointments);
    }

    public List<AppointmentDoctorPreviewDTO> getAppointmentsByDoctorAndDate(String email, Date date) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctorAndDate(email, date);
        return appointmentMapper.mapAppointmentsForDoctor(appointments);
    }

    public void cancelAppointment(Integer appointmentId) throws AppointmentService.PastDateException{
        try {
            appointmentService.cancelAppointment(appointmentId);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void confirmAppointment(Integer appointmentId) {
        appointmentService.confirmAppointment(appointmentId);
    }

    public List<Date> getUnavailableDaysForBloodbank(Integer bloodbankId) {
        return appointmentService.getUnavailableDaysForBloodbank(bloodbankId);
    }

    public DoctorAppointmentsPageDTO getDoctorAppointments(String email, Integer page, Integer numberOfEntries) {
        Page<Appointment> appointments = appointmentService.getDoctorAppointments(email, page, numberOfEntries);
        return appointmentMapper.mapAppointmentsForDoctorPage(appointments);
    }

}
