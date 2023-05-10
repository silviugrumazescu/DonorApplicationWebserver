package com.example.donationapp.service;

import com.example.donationapp.dto.AppointmentDTO;
import com.example.donationapp.dto.AppointmentDoctorPreviewDTO;
import com.example.donationapp.dto.DoctorAppointmentsPageDTO;
import com.example.donationapp.facade.AppointmentFacade;
import com.example.donationapp.factory.NotificationChannel;
import com.example.donationapp.factory.NotificationSenderFactory;
import com.example.donationapp.factory.NotificationType;
import com.example.donationapp.mappers.AppointmentMapper;
import com.example.donationapp.model.Appointment;
import com.example.donationapp.model.BloodBank;
import com.example.donationapp.model.Doctor;
import com.example.donationapp.model.Donor;
import com.example.donationapp.repository.AppointmentRepository;
import com.example.donationapp.repository.BloodBankRepository;
import com.example.donationapp.repository.DoctorRepository;
import com.example.donationapp.repository.DonorRepository;
import com.example.donationapp.utils.DataParser;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class AppointmentService {

    @Autowired
    AppointmentMapper appointmentMapper;
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    BloodBankRepository bloodBankRepository;
    @Autowired
    DataParser dataParser;
    @Autowired
    DonorRepository donorRepository;

    public List<Appointment> getAppointmentsByDoctorAndDate(String email, Date date) {
        List<Appointment> appointments = appointmentRepository.findAll().stream().filter(appointment -> {
            return DateUtils.isSameDay(appointment.getDate(), date) &&
                    appointment.getDoctor().getEmail().equals(email);
        }).collect(Collectors.toList());

        return appointments;
    }

    public void confirmAppointment(Integer appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).get();
        appointment.setConfirmed(true);
        appointmentRepository.save(appointment);
    }

    public Page<Appointment> getDoctorAppointments(String email, Integer page, Integer numberOfEntries) {
        Pageable pageable = (Pageable) PageRequest.of(page, numberOfEntries);
        Page<Appointment> appointments = appointmentRepository.findAllByDoctorEmail(email, pageable);

        return appointments;
    }

    // TO DO: IN SQL
    public ArrayList<Date> getUnavailableDaysForBloodbank(Integer bloodbankId) {
        BloodBank bloodBank = bloodBankRepository.findById(bloodbankId).get();
        List<Appointment> appointments = bloodBank.getAppointments();
        Map<Date, Integer> appointmentsCount = new HashMap<>();
        appointments.forEach(ap -> {
            if(appointmentsCount.containsKey(ap.getDate())) {
                appointmentsCount.replace(ap.getDate(), appointmentsCount.get(ap.getDate()) + 1);
            } else {
                appointmentsCount.put(ap.getDate(), 1);
            }
        });
        Integer maxAppointments = bloodBank.getMaxAppointments();
        ArrayList<Date> disabledDates = new ArrayList<>();
        for(Map.Entry<Date,Integer> entry : appointmentsCount.entrySet()) {
            if(entry.getValue() == maxAppointments) {
                disabledDates.add(entry.getKey());
            }
        }
        return disabledDates;
    }
    // TO DO: MAKE IN SQL
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

    public Appointment createAppointment(String donorEmail, Date date, Integer bloodBankId, NotificationChannel notifyType) throws Exception{
        Date currentDate = new Date();

        if(date.compareTo(currentDate) < 0 && !DateUtils.isSameDay(currentDate, date)) {
            throw new AppointmentService.PastDateException("Can't create appointment for past dates");
        }

        Donor donor = donorRepository.findById(donorEmail).get();
        BloodBank bloodBank = bloodBankRepository.findById(bloodBankId).get();

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
                notifyType);

        appointmentRepository.save(appointment);

        return appointment;
    }

    public void cancelAppointment(Integer appointmentId) throws AppointmentService.PastDateException {
        Appointment appointment = appointmentRepository.findById(appointmentId).get();

        Date currentDate = new Date();
        if(appointment.getDate().compareTo(currentDate) < 0) {
            throw new AppointmentService.PastDateException("Cannot cancel past appointment");
        }

        appointmentRepository.deleteById(appointmentId);
    }

    public List<Appointment> getAppointmentsByDonor(String email) {
        Donor donor = donorRepository.findById(email).get();
        List<Appointment> appointments = donor.getAppointments();
        Collections.sort(appointments, new Comparator<Appointment>() {
            @Override
            public int compare(Appointment o1, Appointment o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        return appointments;
    }

    public List<Appointment> getAppointmentsByDoctor(String email) {
        Doctor doctor = doctorRepository.findById(email).get();
        List<Appointment> appointments = doctor.getAppointments();
        Collections.sort(appointments, new Comparator<Appointment>() {
            @Override
            public int compare(Appointment o1, Appointment o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        return appointments;
    }

    public class PastDateException extends Exception {
        public PastDateException(String error) {
            super(error);
        }
    }

}
