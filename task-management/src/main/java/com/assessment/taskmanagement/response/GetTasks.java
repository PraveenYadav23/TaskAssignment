package com.assessment.taskmanagement.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetTasks {

    private List<TaskDetails> tasks;
    private int totalPages;

}
