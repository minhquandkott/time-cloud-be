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

    @Query(value = "select * from task where task.task_id in (select task_user.task_id from task_user where task_user.user_id = :userId) and task.project_id =:projectId",nativeQuery = true)
    List<TaskEntity> getAllByUserIdAndProjectId(@Param(value = "userId") Integer userId, @Param(value = "projectId") Integer projectId);

    @Query(value = "select distinct task.task_id,task_name,task.create_at,task.modify_at,project_id,task.created_by,task.modified_by \n" +
            "from task join time on time.task_id = task.task_id \n" +
            "where time.user_id = :userId\n" +
            "and task.project_id = :projectId",nativeQuery = true)
    List<TaskEntity> getAllDidDoingByUserAndProjectId(@Param(value = "userId") Integer userId, @Param(value = "projectId") Integer projectId);

    @Modifying
    @Query(value = "delete from task_user where task_user.task_id = :taskId", nativeQuery = true)
    void deleteUsersOfTask(@Param(value = "taskId") Integer task_id);

    @Modifying
    @Query(value = "delete from task_user where task_id = :taskId and user_id = :userId", nativeQuery = true)
    void deleteUserOfTask(@Param(value = "taskId") Integer taskId, @Param(value="userId") Integer userId);

    @Modifying
    @Query(value = "delete from task_user where task_id in (select task_id from task where task.project_id = :projectId) and user_id = :userId",nativeQuery = true)
    void deleteUserOfAllTaskOfProject(@Param(value = "projectId") Integer projectId, @Param(value="userId") Integer userId);

    @Modifying
    @Query(value = "delete from task_user where task_id in (select task_id from task where task.project_id = :projectId);",nativeQuery = true)
    void deleteUsersOfAllTaskOfProject(@Param(value = "projectId") Integer projectId);
    
}
