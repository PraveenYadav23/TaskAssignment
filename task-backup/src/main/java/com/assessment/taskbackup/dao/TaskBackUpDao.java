package com.assessment.taskbackup.dao;

import com.assessment.taskbackup.entity.TaskBackupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskBackUpDao extends JpaRepository<TaskBackupEntity, Long> {
}
