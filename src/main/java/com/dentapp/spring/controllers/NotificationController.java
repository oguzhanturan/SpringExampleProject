package com.dentapp.spring.controllers;

import com.dentapp.spring.payload.request.NotificationRequest;
import com.dentapp.spring.payload.response.NotificationResponse;
import com.dentapp.spring.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(final NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping
    public NotificationResponse getAllNotificationById(NotificationRequest request) {
        return notificationService.getAllNotificationById(request);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping
    public NotificationResponse getAllNotification() {
        return notificationService.getAllNotifications();
    }
}
