package com.dentapp.spring.service;

import com.dentapp.spring.payload.request.NotificationRequest;
import com.dentapp.spring.payload.response.NotificationResponse;

public interface NotificationService {
    NotificationResponse getAllNotificationById(NotificationRequest request);
    NotificationResponse getAllNotifications();
}
