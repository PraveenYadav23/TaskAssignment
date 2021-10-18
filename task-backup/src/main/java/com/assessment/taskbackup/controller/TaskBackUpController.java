package com.assessment.taskbackup.controller;

import com.assessment.taskbackup.request.CreateTaskBackupRequest;
import com.assessment.taskbackup.response.CommonStatusResponse;
import com.assessment.taskbackup.service.TaskBackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/task-backup")
public class TaskBackUpController {

    @Autowired
    private TaskBackupService taskBackupService;

    @PostMapping("/backup-task")
    public CommonStatusResponse createTask(
            @Valid @RequestBody CreateTaskBackupRequest taskRequest,
            HttpServletRequest request
    ){
        taskBackupService.createTaskBackup(taskRequest);
        CommonStatusResponse response = new CommonStatusResponse("Success");
        return response;
    }

}