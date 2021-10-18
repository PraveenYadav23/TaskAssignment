package com.assessment.taskmanagement.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TaskBackupRequest {

    private String name;
    private String description;
    private Date createdDate;
    private Date lastDate;
    private Long taskId;
    private Date completedDate;
    private boolean isComplete;
    private String operation;

}
