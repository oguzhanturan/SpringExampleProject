package com.dentapp.spring.payload.response;

import com.dentapp.spring.models.Issue;
import lombok.Data;

import java.io.Serializable;

@Data
public class GetIssueResponse implements Serializable {
    private Issue issue;
}
