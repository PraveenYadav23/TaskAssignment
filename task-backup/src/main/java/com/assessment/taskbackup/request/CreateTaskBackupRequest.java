package com.assessment.taskbackup.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreateTaskBackupRequest {

    private Long taskId;

    private String name;
    private String description;

    private Date createdDate;
    private Date lastDate;

    private String operationType;

}
