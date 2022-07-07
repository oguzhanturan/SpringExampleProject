package com.dentapp.spring.util;

import com.dentapp.spring.models.Issue;
import com.dentapp.spring.models.Notification;
import com.dentapp.spring.models.StatusType;
import com.dentapp.spring.payload.response.NotificationResponse;
import com.dentapp.spring.repository.IssueRepository;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class NotificationUtil {

    public static NotificationResponse createNotificationMessage(
            final List<Issue> byPossibleEndDateGreaterThanEqual, final List<Issue> byPossibleEndDate, IssueRepository issueRepository) {
        List<Notification> notificationList = new ArrayList<>();
        //geçikmedeki kayıtlar
        byPossibleEndDateGreaterThanEqual.forEach((issue -> {
            issue.setCurrentStatus(StatusType.delay);
            issueRepository.save(issue);
            Notification notification = Notification.builder()
                    .message(issue.getName()+" başlıklı " + issue.getIssueId() + " nolu iş kaydı gecikmededir.")
                    .title("Gecikmede")
                    .user(issue.getUser())
                    .createDate(LocalDateTime.now())
                    .type("WARNING")
                    .issue(issue).build();
            notificationList.add(notification);
        }));
        //son günü olanlar
        byPossibleEndDate.forEach((issue -> {
            issue.setCurrentStatus(StatusType.delay);
            issueRepository.save(issue);
            Notification notification = Notification.builder()
                    .message(issue.getName()+" başlıklı " + issue.getIssueId() + " nolu iş kaydı için son gün.")
                    .title("Bidirim")
                    .user(issue.getUser())
                    .createDate(LocalDateTime.now())
                    .type("INFO")
                    .issue(issue).build();
            notificationList.add(notification);
        }));

        return new NotificationResponse(notificationList);
    }
}
