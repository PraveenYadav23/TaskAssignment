package com.assessment.taskmanagement.controller;

import com.assessment.taskmanagement.request.CreateTaskRequest;
import com.assessment.taskmanagement.request.UpdateTaskRequest;
import com.assessment.taskmanagement.response.CommonStatusResponse;
import com.assessment.taskmanagement.response.GetTasks;
import com.assessment.taskmanagement.response.TaskDetails;
import com.assessment.taskmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/get-tasks")
    public GetTasks getTasks(
            //TODO use pageable object
             @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "5") int limit,
            @RequestParam(value = "userId") Long userId,
            HttpServletRequest request
    ){
        //TODo avoid create instance
        GetTasks tasks = taskService.getAllTasks(page, limit, userId);
        return tasks;
    }

    @GetMapping("/{taskId}")
    public TaskDetails getTaskDetails(
            @PathVariable("taskId") Long taskId,
            @RequestParam(value = "userId") Long userId,
            HttpServletRequest request){

        TaskDetails taskDetails = taskService.getTaskDetails(taskId, userId);
        return taskDetails;
    }

    @DeleteMapping("/delete-task")
    public CommonStatusResponse deleteTask(@RequestParam("taskId") Long taskId, HttpServletRequest request){
        taskService.deleteTaskById(taskId);
        CommonStatusResponse response = new CommonStatusResponse("Success");
        return response;
    }

    @PostMapping("/create-task")
    public CommonStatusResponse createTask(
            @Valid @RequestBody CreateTaskRequest createTaskRequest,
            HttpServletRequest request
    ){
        taskService.createNewTask(createTaskRequest);
        CommonStatusResponse response = new CommonStatusResponse("Success");
        return response;
    }

    @PutMapping("/update-task")
    public CommonStatusResponse updateTask(
            @Valid @RequestBody UpdateTaskRequest updateTaskRequest,
            HttpServletRequest request
    ){
        taskService.updateExistingTask(updateTaskRequest);
        CommonStatusResponse response = new CommonStatusResponse("Success");
        return response;
    }

    @PutMapping("/mark-done/{taskId}")
    public CommonStatusResponse markDone(@PathVariable(name = "taskId") Long taskId,
        HttpServletRequest request){
        taskService.markTaskDone(taskId);
        CommonStatusResponse response = new CommonStatusResponse("Success");
        return response;
    }

    @PostMapping(value = "/create-task1", consumes = {"multipart/form-data"})
    public CommonStatusResponse createTask1(
            @Valid @RequestPart("data") CreateTaskRequest createTaskRequest,
            @RequestPart("file") MultipartFile file,
            HttpServletRequest request
    ) throws IOException {
        taskService.createNewTask1(createTaskRequest,file);
        CommonStatusResponse response = new CommonStatusResponse("Success");
        return response;
    }

}
