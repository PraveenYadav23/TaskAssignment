package com.assessment.taskmanagement.service;

import com.assessment.taskmanagement.Entity.TaskEntity;
import com.assessment.taskmanagement.Entity.UserEntity;
import com.assessment.taskmanagement.converter.MapperUtil;
import com.assessment.taskmanagement.dao.TaskDao;
import com.assessment.taskmanagement.dao.UserDao;
import com.assessment.taskmanagement.exception.TaskNotFoundException;
import com.assessment.taskmanagement.exception.UserNotFoundException;
import com.assessment.taskmanagement.request.CreateTaskRequest;
import com.assessment.taskmanagement.request.TaskBackupRequest;
import com.assessment.taskmanagement.request.UpdateTaskRequest;
import com.assessment.taskmanagement.response.GetTasks;
import com.assessment.taskmanagement.response.TaskDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TaskService {

    @Autowired
    private TaskDao taskDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RetryService retryService;
    @Autowired
    private FileService fileService;


    private final Logger logger = LogManager.getLogger(this.getClass());

    public GetTasks getAllTasks(int page, int limit, Long userId) {

        if(page > 0){
            page = page - 1;
        }
        Pageable pageable = PageRequest.of(page, limit);

        Page<TaskEntity> taskEntities = taskDao.findAllByUserId(userId, pageable);

        //TODO write a util class to map a list as generic
/*
        List<TaskDetails> taskDetails = taskEntities.stream()
                .map(taskEntity -> {
                    ModelMapper mapper = new ModelMapper();
                    TaskDetails task = mapper.map(taskEntity,TaskDetails.class);
                    task.setAssigned_by(taskEntity.getAssignedByUser().getId());
                    task.setAssigned_to(taskEntity.getAssignedToUser().getId());
                    return task;
                }).collect(Collectors.toList());
*/
        MapperUtil<TaskEntity, TaskDetails> mapperUtil = new MapperUtil<>();
        List<TaskDetails> taskDetails = mapperUtil.mapList(taskEntities.getContent(), TaskDetails.class);
        GetTasks tasks = new GetTasks();
        tasks.setTasks(taskDetails);
        tasks.setTotalPages(taskEntities.getTotalPages());
        return tasks;
    }

    public TaskDetails getTaskDetails(Long taskId, Long userId) {

        TaskEntity taskEntity = taskDao.getTaskDetailsByUserId(taskId, userId).orElseThrow(
                () -> new TaskNotFoundException("Task Not Found")
        );
/*
        ModelMapper mapper = new ModelMapper();
        TaskDetails taskDetails = mapper.map(taskEntity, TaskDetails.class);
*/
        MapperUtil<TaskEntity, TaskDetails> mapperUtil = new MapperUtil<>();
        TaskDetails taskDetails = mapperUtil.map(taskEntity, TaskDetails.class);
        taskDetails.setAssigned_by(taskEntity.getAssignedByUser().getId());
        taskDetails.setAssigned_to(taskEntity.getAssignedToUser().getId());
        return taskDetails;
    }

    @Transactional
    public void deleteTaskById(Long taskId) {

        // check task available or not
        if(!taskDao.isTaskExist(taskId)){
            throw new TaskNotFoundException("Task Not Found");
        }
        else{
//            taskDao.deleteById(taskId);
            TaskEntity taskEntity = taskDao.findById(taskId).get();
/*
            ModelMapper mapper = new ModelMapper();
            TaskBackupRequest taskBackupRequest = mapper.map(taskEntity, TaskBackupRequest.class);
*/
            MapperUtil<TaskEntity, TaskBackupRequest> mapperUtil = new MapperUtil<>();
            TaskBackupRequest taskBackupRequest = mapperUtil.map(taskEntity, TaskBackupRequest.class);
            taskBackupRequest.setOperation("DELETE");

            // saving data in backup table
            retryService.saveBackupTaskMethod(taskBackupRequest);
        }
    }

    @Transactional
    public void createNewTask(CreateTaskRequest createTaskRequest) {

        UserEntity assignedByUser = userDao.findById(createTaskRequest.getAssignedBy()).orElseThrow(
                () -> new UserNotFoundException("User Not Found")
        );

        UserEntity assignedToUser = userDao.findById(createTaskRequest.getAssignedTo()).orElseThrow(
                () -> new UserNotFoundException("User Not Found")
        );

/*
        ModelMapper mapper = new ModelMapper();
        TaskEntity taskEntity = mapper.map(createTaskRequest, TaskEntity.class);
*/
        MapperUtil<CreateTaskRequest, TaskEntity> mapperUtil = new MapperUtil<>();
        TaskEntity taskEntity = mapperUtil.map(createTaskRequest, TaskEntity.class);
        taskEntity.setComplete(false);
        taskEntity.setAssignedByUser(assignedByUser);
        taskEntity.setAssignedToUser(assignedToUser);

        taskDao.saveAndFlush(taskEntity);


        TaskBackupRequest taskBackupRequest = mapperUtil.map(taskEntity, TaskBackupRequest.class);
        taskBackupRequest.setOperation("CREATE");

        // saving data in backup table
        retryService.saveBackupTaskMethod(taskBackupRequest);
    }


    @Transactional
    public void updateExistingTask(UpdateTaskRequest updateTaskRequest) {

        // check task available or not
        if(!taskDao.isTaskExist(updateTaskRequest.getId())){
            throw new TaskNotFoundException("Task Not Found");
        }
        TaskEntity taskEntity = taskDao.findById(updateTaskRequest.getId()).get();
        ModelMapper mapper = new ModelMapper();
        mapper.map(updateTaskRequest, taskEntity);
        if(updateTaskRequest.getAssignedTo() != null){
            UserEntity assignedUser = userDao.findById(updateTaskRequest.getAssignedTo()).orElseThrow(
                    () ->  new UserNotFoundException("User Not Found")
            );
            taskEntity.setAssignedToUser(assignedUser);
        }
        taskDao.saveAndFlush(taskEntity);
/*
        TaskBackupRequest taskBackupRequest = new TaskBackupRequest();
        mapper.map(taskEntity, taskBackupRequest);
*/

        MapperUtil<TaskEntity, TaskBackupRequest> mapperUtil = new MapperUtil<>();
        TaskBackupRequest taskBackupRequest = mapperUtil.map(taskEntity, TaskBackupRequest.class);

        taskBackupRequest.setOperation("UPDATE");

        // saving data in backup table
        retryService.saveBackupTaskMethod(taskBackupRequest);
    }

    public void markTaskDone(Long taskId) {

        TaskEntity taskEntity = taskDao.findById(taskId).orElseThrow(
                () -> new TaskNotFoundException("Task Not Found")
        );

        taskEntity.setComplete(true);
        taskEntity.setCompletedDate(new Date());
        taskDao.save(taskEntity);

    }

    @Transactional
    public void createNewTask1(CreateTaskRequest createTaskRequest, MultipartFile file) throws IOException {

        UserEntity assignedByUser = userDao.findById(createTaskRequest.getAssignedBy()).orElseThrow(
                () -> new UserNotFoundException("User Not Found")
        );

        UserEntity assignedToUser = userDao.findById(createTaskRequest.getAssignedTo()).orElseThrow(
                () -> new UserNotFoundException("User Not Found")
        );

        ModelMapper mapper = new ModelMapper();
        TaskEntity taskEntity = mapper.map(createTaskRequest, TaskEntity.class);
        taskEntity.setComplete(false);
        taskEntity.setAssignedByUser(assignedByUser);
        taskEntity.setAssignedToUser(assignedToUser);

        taskDao.saveAndFlush(taskEntity);

        TaskBackupRequest taskBackupRequest = mapper.map(taskEntity, TaskBackupRequest.class);
        taskBackupRequest.setOperation("CREATE");

        // saving data in backup table
        retryService.saveBackupTaskMethod(taskBackupRequest);
        if(file != null) {
            String filePath = fileService.uploadFile(taskEntity.getId(), file);
            taskEntity.setFilePath(filePath);
            taskDao.save(taskEntity);
        }

    }
}
