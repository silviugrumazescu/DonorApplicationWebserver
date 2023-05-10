package com.example.donationapp.factory;

import com.example.donationapp.service.EmailService;
import com.example.donationapp.service.NotificationService;
import com.example.donationapp.service.SmsService;

public class NotificationSenderFactory {
    public static NotificationService getNotificationService(NotificationChannel notificationChannel) {
        if(notificationChannel == NotificationChannel.EMAIL_NOTIFICATION) {
            return new EmailService();
        } else if(notificationChannel == NotificationChannel.SMS_NOTIFICATION) {
            return new SmsService();
        }
        return null;
    }
}
