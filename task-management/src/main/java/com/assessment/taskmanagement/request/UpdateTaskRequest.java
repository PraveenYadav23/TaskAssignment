package com.assessment.taskmanagement.request;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Getter
@Setter
@XmlRootElement(name = "task")
public class UpdateTaskRequest {

    @XmlElement
    private Long id;
    @XmlElement
    private String name;
    @XmlElement
    private String description;
    @XmlElement
    private Date createdDate;
    @XmlElement
    private Date lastDate;
    @XmlElement
    private boolean isComplete;
    @XmlElement
    private Long assignedTo;

}
