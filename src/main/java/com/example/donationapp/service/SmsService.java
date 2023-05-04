package com.example.donationapp.service;

import ClickSend.Api.SmsApi;
import ClickSend.ApiClient;
import ClickSend.Model.SmsMessage;
import ClickSend.Model.SmsMessageCollection;
import com.example.donationapp.factory.NotificationType;
import com.example.donationapp.model.Appointment;
import com.example.donationapp.utils.DataParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

public class SmsService implements NotificationService{

    private ApiClient clickSendApiClient;

    @Value("${clickSend-username}")
    private String clickSendUsername;
    @Value("${clickSend-apiKey}")
    private String clickSendApiKey;


    public SmsService() {

        this.clickSendApiClient = new ApiClient();
        clickSendApiClient.setUsername(clickSendUsername);
        clickSendApiClient.setPassword(clickSendApiKey);
    }

    public void send(NotificationType notificationType, Appointment appointment) {

        SmsApi smsApi = new SmsApi(clickSendApiClient);
        SmsMessage smsMessage = new SmsMessage();

        String message;
        if(notificationType == NotificationType.CONFIRM) {
            message = composeConfirmSms(appointment);
        } else {
            message = composeReminderSms(appointment);
        }
        smsMessage.body(message);
        smsMessage.to(appointment.getDonor().getPhoneNumber());
        smsMessage.source("Bloodbank");

        SmsMessageCollection smsMessageCollection = new SmsMessageCollection();
        smsMessageCollection.messages(Arrays.asList(smsMessage));

        try {
            smsApi.smsSendPost(smsMessageCollection);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
