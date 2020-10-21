package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer>
{
    List<TaskEntity> getAllByProjectId(Integer projectId);

    @Modifying
    @Query(value = "insert into task_user(user_id, task_id) values(:userId, :taskId)", nativeQuery = true)
    void addUserToTask(@Param(value = "userId") Integer userId,@Param(value = "taskId") Integer task_id);
    
    @Query(value = "select * from task where task.task_id in (select task_user.task_id from task_user where task_user.user_id = :taskId)",nativeQuery = true)
    List<TaskEntity> getAllByUserId(@Param(value = "taskId") Integer task_id);

    @Query(value = "select * from task where task.task_id in (select task_user.task_id from task_user where task_user.user_id = :taskId) and task.project_id =:projectId",nativeQuery = true)
    List<TaskEntity> getAllByUserIdAndProjectId(@Param(value = "taskId") Integer task_id, @Param(value = "projectId") Integer projectId);

    @Modifying
    @Query(value = "delete from task_user where task_user.task_id = :taskId", nativeQuery = true)
    void deleteUsersOfTask(@Param(value = "taskId") Integer task_id);

    @Modifying
    @Query(value = "delete from task_user where task_id = :taskId and user_id = :userId", nativeQuery = true)
    void deleteUserOfTask(@Param(value = "taskId") Integer taskId, @Param(value="userId") Integer userId);

}
