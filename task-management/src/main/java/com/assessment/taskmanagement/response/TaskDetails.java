package com.assessment.taskmanagement.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TaskDetails {

    private Long id;
    private String name;
    private String description;
    private Date createdDate;
    private Date lastDate;
    private Date completedDate;
    private boolean isComplete;
    private Long assigned_by;
    private Long assigned_to;

}
