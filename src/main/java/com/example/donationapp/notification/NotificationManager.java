package com.example.donationapp.notification;

import com.example.donationapp.factory.NotificationSenderFactory;
import com.example.donationapp.factory.NotificationType;
import com.example.donationapp.model.Appointment;
import com.example.donationapp.service.NotificationService;

public class NotificationManager {
    public void notifyAppointmentCreated(Appointment appointment) {
        NotificationService notificationService =
                NotificationSenderFactory.getNotificationService(appointment.getNotificationType());

        notificationService.send(NotificationType.CONFIRM, appointment);
    }

    public void notifyAppointmentReminder(Appointment appointment) {
        NotificationService notificationService =
                NotificationSenderFactory.getNotificationService(appointment.getNotificationType());

        notificationService.send(NotificationType.REMINDER, appointment);
    }

}
