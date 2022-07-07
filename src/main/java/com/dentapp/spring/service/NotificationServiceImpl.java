package com.dentapp.spring.service;

import com.dentapp.spring.models.Issue;
import com.dentapp.spring.models.Notification;
import com.dentapp.spring.payload.request.NotificationRequest;
import com.dentapp.spring.payload.response.NotificationResponse;
import com.dentapp.spring.repository.IssueRepository;
import com.dentapp.spring.util.NotificationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    private final IssueRepository issueRepository;

    public NotificationServiceImpl(final IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @Override
    public NotificationResponse getAllNotificationById(NotificationRequest request) {
        return null;
    }

    @Override
    public NotificationResponse getAllNotifications() {
        //beklenen teslim tarihi alanı bugunden büyük olanlar
        List<Issue> byPossibleEndDateGreaterThanEqual = issueRepository.findByPossibleEndDateLessThanEqual(LocalDateTime.now());
        //işin başlangıç tarihi bugun olanlar
        List<Issue> byPossibleEndDate = issueRepository.findByPossibleEndDate(LocalDateTime.now());

        return NotificationUtil.createNotificationMessage(byPossibleEndDateGreaterThanEqual, byPossibleEndDate, issueRepository);
    }
}
