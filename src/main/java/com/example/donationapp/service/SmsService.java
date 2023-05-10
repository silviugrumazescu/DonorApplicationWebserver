package com.example.donationapp.service;

import ClickSend.Api.SmsApi;
import ClickSend.ApiClient;
import ClickSend.Model.SmsMessage;
import ClickSend.Model.SmsMessageCollection;
import com.example.donationapp.factory.NotificationType;
import com.example.donationapp.model.Appointment;
import com.example.donationapp.utils.DataParser;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

public class SmsService implements NotificationService{

    private final String ACCOUNT_SID = "AC101f3250dadc796a689a4edba7ba6c45";
    private String AUTH_TOKEN = "443c9801f437772ceee364da1da8a141";
    private String TWILIO_NUMBER = "+13204387855";


    public SmsService() {
    }

    public void send(NotificationType notificationType, Appointment appointment) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String text;
        if(notificationType == NotificationType.CONFIRM) {
            text = composeConfirmSms(appointment);
        } else {
            text = composeReminderSms(appointment);
        }

        Message message = Message.creator(
                new PhoneNumber("+40756326022"),
                new PhoneNumber(TWILIO_NUMBER),
                text
        ).create();
        System.out.println("****************SMS SENT*********");
    }

    public String composeReminderSms(Appointment appointment) {
        DataParser dataParser = new DataParser();

        String name = appointment.getDonor().getName();
        String location = appointment.getBloodBank().getName();
        String date = dataParser.formatDate(appointment.getDate());

        return "Hello " + name + "\n" + "We would like to remind you that your appointment at " + location + " for blood" +
                "donation is tomorrow (" + date + ").";
    }

    public String composeConfirmSms(Appointment appointment) {
        DataParser dataParser = new DataParser();

        String name = appointment.getDonor().getName();
        String location = appointment.getBloodBank().getName();
        String date = dataParser.formatDate(appointment.getDate());

        return "Hello " + name + "\n" + "Your appointment at " + location + " on " + date + "has been confirmed.";
    }

}
