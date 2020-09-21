package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Integer>
{
    List<TaskEntity> getAllByProjectId(Integer projectId);

    @Modifying
    @Query(value = "insert into task_user(user_id, task_id) values(:userId, :taskId)", nativeQuery = true)
    void addUserToTask(@Param(value = "userId") Integer userId,@Param(value = "takId") Integer task_id);
}
