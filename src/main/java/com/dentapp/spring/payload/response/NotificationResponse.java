package com.dentapp.spring.payload.response;

import com.dentapp.spring.models.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NotificationResponse {
    private List<Notification> notificationList;
}
