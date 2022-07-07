package com.dentapp.spring.payload.response;

import com.dentapp.spring.models.Issue;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GetAllIssueResponse implements Serializable {
    private List<Issue> issueList;
}
