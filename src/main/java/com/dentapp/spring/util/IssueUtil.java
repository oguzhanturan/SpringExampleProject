package com.dentapp.spring.util;

import com.dentapp.spring.models.Issue;
import com.dentapp.spring.repository.IssueRepository;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@UtilityClass
public class IssueUtil {

    public static String generateUuid(IssueRepository issueRepository){
        final LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        final List<Issue> issueList = issueRepository.findByCreateDate(now);
        if(issueList.size() > 0) {
            final Optional<Issue> max = issueList.stream().max(Comparator.comparing(Issue::getId));
            return LocalDate.now().format(formatter).toString().replace("-", "") + "-" + Math.addExact(max.get().getId(), 1);
        }
        return LocalDate.now().format(formatter).toString().replace("-", "") + "-" + "1";
    }
}
