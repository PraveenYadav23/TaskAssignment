package com.assessment.taskmanagement.dao;

import com.assessment.taskmanagement.Entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskDao extends JpaRepository<TaskEntity, Long> {

    @Query("FROM TaskEntity as t WHERE t.assignedToUser.id=:userId")
    Page<TaskEntity> findAllByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT CASE WHEN count(*) > 0 THEN true ELSE false END " +
            "FROM TaskEntity WHERE id=:taskId")
    boolean isTaskExist(@Param("taskId") Long taskId);

    @Query("FROM TaskEntity t WHERE t.id=:taskId AND t.assignedToUser.id=:userId")
    Optional<TaskEntity> getTaskDetailsByUserId(@Param("taskId") Long taskId, @Param("userId") Long userId);

    @Query("FROM TaskEntity t WHERE t.isComplete=false AND lastDate < ?1")
    List<TaskEntity> getDeadlineCrossedTask(Date currentDate);
}
