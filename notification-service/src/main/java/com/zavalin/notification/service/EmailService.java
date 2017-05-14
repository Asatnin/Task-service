package com.zavalin.notification.service;

public interface EmailService {
    void sendNotificationMessage(String to, String subject, String text);
}
