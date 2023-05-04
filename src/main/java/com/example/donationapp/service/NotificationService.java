package com.example.donationapp.service;

import com.example.donationapp.factory.NotificationType;
import com.example.donationapp.model.Appointment;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;

public interface NotificationService {

    public void send(NotificationType notificationType, Appointment appointment);

}
