package com.assessment.taskmanagement.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "task")
@Getter
@Setter
@Data
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "last_date")
    private Date lastDate;

    @Column(name = "completed_date")
    private Date completedDate;

    @Column(name = "complete")
    private boolean isComplete;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "assigned_by")
    private UserEntity assignedByUser;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "assigned_to")
    private UserEntity assignedToUser;

    @Column(name = "file_path")
    private String filePath;

}
