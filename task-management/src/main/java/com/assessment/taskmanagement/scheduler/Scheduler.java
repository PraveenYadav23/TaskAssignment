package com.assessment.taskmanagement.scheduler;

import com.assessment.taskmanagement.Entity.TaskEntity;
import com.assessment.taskmanagement.Entity.UserEntity;
import com.assessment.taskmanagement.dao.TaskDao;
import com.assessment.taskmanagement.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class Scheduler {

    @Autowired
    private TaskDao taskDao;
    @Autowired
    private NotificationService notificationService;

    @Scheduled(cron = "${scheduler.taskdeadline}")
    public void schedulerForTaskDeadlineNotification(){
        Date currentDate = new Date();
        List<TaskEntity> taskEntityList = taskDao.getDeadlineCrossedTask(currentDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
/*
        taskEntityList.stream().forEach(
                taskEntity -> {
                    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                    simpleMailMessage.setSubject("Task Deadline Exceed");
                    simpleMailMessage.setText("Task Name: "+taskEntity.getName()
                            +"\nTask Description: "+taskEntity.getDescription()
                            +"\nLast Date: "+taskEntity.getLastDate());
                    simpleMailMessage.setFrom("Testing");
                    simpleMailMessage.setTo(taskEntity.getAssignedToUser().getEmail());
                    notificationService.sendSimpleEmail(simpleMailMessage);
                }
        );
*/
        for(TaskEntity taskEntity : taskEntityList){
            UserEntity userEntity = taskEntity.getAssignedToUser();
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setSubject("Task Deadline Exceed");
            simpleMailMessage.setText("Hi "+userEntity.getName()+", Your task exceeding the deadline " +
                    "\n\nTask Name: "+taskEntity.getName()
                    +"\nTask Description: "+taskEntity.getDescription()
                    +"\nLast Date: "+simpleDateFormat.format(taskEntity.getLastDate())
                    +"\n\nThanks");
            simpleMailMessage.setFrom("Testing");
            simpleMailMessage.setTo(userEntity.getEmail());
            notificationService.sendSimpleEmail(simpleMailMessage);
        }

    }
}
