package com.assessment.taskbackup.service;

import com.assessment.taskbackup.dao.TaskBackUpDao;
import com.assessment.taskbackup.entity.TaskBackupEntity;
import com.assessment.taskbackup.request.CreateTaskBackupRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskBackupService {

    @Autowired
    private TaskBackUpDao taskBackUpDao;

    public void createTaskBackup(CreateTaskBackupRequest taskRequest) {

        ModelMapper mapper = new ModelMapper();
        TaskBackupEntity taskBackupEntity = mapper.map(taskRequest, TaskBackupEntity.class);
        taskBackUpDao.save(taskBackupEntity);
    }
}
