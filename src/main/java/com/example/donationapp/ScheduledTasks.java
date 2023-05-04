package com.example.donationapp;

import com.example.donationapp.factory.NotificationSenderFactory;
import com.example.donationapp.factory.NotificationType;
import com.example.donationapp.model.Appointment;
import com.example.donationapp.repository.AppointmentRepository;
import com.example.donationapp.service.AppointmentService;
import com.example.donationapp.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduledTasks {
    @Autowired
    AppointmentRepository appointmentRepository;

    @Scheduled(fixedRate = 86400000)
    public void notifyAppointmentsReminder() {
        Date currentDate = new Date();

        List<Appointment> appointments =  appointmentRepository.findAll();

        appointments.forEach(appointment -> {

            long diffInMillies = Math.abs(currentDate.getTime() - appointment.getDate().getTime());
            if(diffInMillies > 86400000 && diffInMillies < 172800000) { // > 24 h, < 48 h
                NotificationService notificationService =
                        NotificationSenderFactory.getNotificationService(appointment.getNotificationType());
                notificationService.send(NotificationType.REMINDER, appointment);

                System.out.println("Email send for app" + appointment.getId());
            }
        });

    }


}
