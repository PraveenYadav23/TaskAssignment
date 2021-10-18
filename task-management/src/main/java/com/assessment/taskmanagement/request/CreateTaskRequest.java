package com.assessment.taskmanagement.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Getter
@Setter
@XmlRootElement(name = "task")
public class CreateTaskRequest {

    @NotNull(message = "Task Name can't be null")
    @NotEmpty(message = "Task name can't be empty")
    @XmlElement
    private String name;

    @NotNull(message = "Task description can't be null")
    @NotEmpty(message = "Task description can't be empty")
    @XmlElement
    private String description;

    @NotNull(message = "created date can't be null")
    @XmlElement
    private Date createdDate;

    @NotNull(message = "last date can't be null")
    @XmlElement
    private Date lastDate;

    @NotNull(message = "assigned by user can't be null")
    @XmlElement
    private Long assignedBy;

    @NotNull(message = "assigned to user can't be null")
    @XmlElement
    private Long assignedTo;

}
