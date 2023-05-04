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
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    BloodBankRepository bloodBankRepository;
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    DataParser dataParser;

    public DonorEditDTO getDonor(String email) {
        Donor d = donorRepository.findById(email).get();

        DonorEditDTO donor = new DonorEditDTO(
                email,
                d.getCNP(),
                d.getName(),
                "",
                d.getDistrict().name(),
                d.getBloodType().name()
        );
        return donor;
    }

    public void updateDonor(DonorEditDTO donorEditDTO) throws Exception{
        Donor donor = donorRepository.findById(donorEditDTO.email).get();
        Donor updatedDonor;
        try {
            updatedDonor = new Donor(
                    donorEditDTO.name,
                    donorEditDTO.email,
                    donor.getPassword(),
                    dataParser.parseDistrict(donorEditDTO.district),
                    Role.Donor,
                    dataParser.parseBloodType(donorEditDTO.bloodType),
                    donorEditDTO.CNP,
                    donor.getPhoneNumber()
            );
        } catch (Exception ex) {
            throw ex;
        }
        donorRepository.save(updatedDonor);
    }

    public Doctor getDoctorMinAppointments(BloodBank bloodBank) {
        List<Doctor> doctors = bloodBank.getDoctors();

        Collections.sort(doctors, new Comparator<Doctor>() {
            @Override
            public int compare(Doctor d1, Doctor d2) {
                int d1Size = d1.getAppointments().size();
                int d2Size = d2.getAppointments().size();

                if(d1Size < d2Size) return -1;
                else if (d1Size > d2Size) return 1;
                else return 0;
            }
        });

        return doctors.get(0);

    }

    public void createAppointment(AppointmentDTO appointmentDTO) throws Exception{
        SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd", Locale.ENGLISH);
        Date date;
        try {
            date = formatter.parse(appointmentDTO.date);
        } catch (Exception ex) {
            throw new InvalidDateException("Invalid date");
        }

        Date currentDate = new Date();
        if(date.compareTo(currentDate) < 0 && !DateUtils.isSameDay(currentDate, date)) {
            throw new PastDateException("Can't create appointment for past dates");
        }

        Donor donor = donorRepository.findById(appointmentDTO.donorEmail).get();
        BloodBank bloodBank = bloodBankRepository.findById(appointmentDTO.bloodbankId).get();

        int numberOfAppointmentsInDate = (int)bloodBank.getAppointments().stream().filter(
                ap -> DateUtils.isSameDay(ap.getDate(), date)).count();

        if(numberOfAppointmentsInDate == bloodBank.getMaxAppointments()) {
            throw new Exception("Maximum number of appointments reached");
        }

        Doctor doctor = getDoctorMinAppointments(bloodBank);

        Appointment appointment = new Appointment(
                donor,
                doctor,
                bloodBank,
                date,
                dataParser.parseNotificationChannel(appointmentDTO.notifyType));

        appointmentRepository.save(appointment);

        // de pus in facade
        NotificationService notificationService = NotificationSenderFactory.getNotificationService(NotificationChannel.EMAIL_NOTIFICATION);
        notificationService.send(NotificationType.CONFIRM, appointment);

    }

    public List<AppointmentDonorPreviewDTO> getDonorAppointments(String email) {
        Donor donor = donorRepository.findById(email).get();
        donor.getAppointments();
        List<Appointment> appointments = donor.getAppointments();

        Collections.sort(appointments, new Comparator<Appointment>() {
            @Override
            public int compare(Appointment o1, Appointment o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        List<AppointmentDonorPreviewDTO> appointmentDonorPreviewDTOS = new ArrayList<AppointmentDonorPreviewDTO>();

        appointments.forEach(appointment -> {
            SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd", Locale.ENGLISH);
            appointmentDonorPreviewDTOS.add(new AppointmentDonorPreviewDTO(
                    appointment.getId(),
                    appointment.getDoctor().getName(),
                    appointment.getBloodBank().getName(),
                    appointment.getBloodBank().getAddress(),
                    formatter.format(appointment.getDate()),
                    appointment.getConfirmed()
            ));
        });

        return appointmentDonorPreviewDTOS;
    }

    public void cancelAppointment(Integer appointmentId) throws PastDateException{

        Appointment appointment = appointmentRepository.findById(appointmentId).get();

        Date currentDate = new Date();
        if(appointment.getDate().compareTo(currentDate) < 0) {
            throw new PastDateException("Cannot cancel past appointment");
        }

        appointmentRepository.deleteById(appointmentId);
    }

    public class PastDateException extends Exception {
        public PastDateException(String error) {
            super(error);
        }
    }

    public class InvalidDateException extends Exception {
        public InvalidDateException(String error) {
            super(error);
        }
    }


}
