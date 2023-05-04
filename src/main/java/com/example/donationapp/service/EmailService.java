package com.example.donationapp.service;

import ch.qos.logback.core.util.DatePatternToRegexUtil;
import com.example.donationapp.factory.NotificationType;
import com.example.donationapp.model.Appointment;
import com.example.donationapp.utils.DataParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

public class EmailService implements NotificationService{

    private JavaMailSenderImpl javaMailSender;

    public EmailService() {
        javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);

        javaMailSender.setUsername("bloodbankteam123@gmail.com");
        javaMailSender.setPassword("ctujpclsylmxrvue");

        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
    }

    public void send(NotificationType notificationType, Appointment appointment) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(appointment.getDonor().getEmail());
        simpleMailMessage.setSubject("From the bloodbank team");

        String message;
        if(notificationType == NotificationType.REMINDER) {
            message = composeReminderEmail(appointment);
        } else {
            message = composeConfirmEmail(appointment);
        }
        simpleMailMessage.setText(message);
        try {
            javaMailSender.send(simpleMailMessage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public String composeReminderEmail(Appointment appointment) {
        DataParser dataParser = new DataParser();

        String name = appointment.getDonor().getName();
        String location = appointment.getBloodBank().getName();
        String date = dataParser.formatDate(appointment.getDate());

        return "Hello " + name + "\n" + "We would like to remind you that your appointment at " + location + " for blood" +
                "donation is tomorrow (" + date + ").";
    }

    public String composeConfirmEmail(Appointment appointment) {
        DataParser dataParser = new DataParser();

        String name = appointment.getDonor().getName();
        String location = appointment.getBloodBank().getName();
        String date = dataParser.formatDate(appointment.getDate());

        return "Hello " + name + "\n" + "Your appointment at " + location + " on " + date + "has been confirmed.";
    }

}
