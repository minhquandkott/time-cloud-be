package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.TimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;

@Repository
public interface TimeRepository extends JpaRepository<TimeEntity, Integer> {

    List<TimeEntity> getAllByUserId(Integer userId);
    List<TimeEntity> getAllByTaskId(Integer taskId);
    List<TimeEntity> getAllByDescription(String description);
    void deleteByTaskId (Integer taskId);

    @Query(value = "select extract(epoch from sum(end_time - start_time)) as totalTime from time where task_id =:taskId", nativeQuery = true)
    Float sumTimeByTaskId(@Param(value = "taskId") Integer taskId);

    @Query(value = "select extract(epoch from sum(end_time - start_time)) as totalTime from time where user_id =:userId", nativeQuery = true)
    Float sumTimeByUserId(@Param(value = "userId") Integer userId);

    @Query(value = "select extract(epoch from sum(end_time - start_time)) as totalTime from time where task_id in (select task_id from task where project_id =:projectId)", nativeQuery = true)
    Float sumTimeByProjectId(@Param(value = "projectId") Integer projectId);

    @Query(value = "select extract(epoch from sum(end_time-start_time)) as totalTime from time where user_id = :userId and task_id = :taskId",nativeQuery = true)
    Float sumTimeByUserTask(@Param(value = "userId") Integer userId, @Param(value = "taskId") Integer taskId);

    @Query(value = "select extract(epoch from sum(end_time-start_time)) as totalTime from time join task on time.task_id = task.task_id where user_id = :userId and project_id = :projectId",nativeQuery = true)
    Float sumTimeByUserProject(@Param(value = "userId") Integer userId, @Param(value = "projectId") Integer projectId);

    @Query(value = "select extract(epoch from sum(end_time-start_time)) as totalTime from time join task on time.task_id = task.task_id where user_id = :userId and description = :description",nativeQuery = true)
    Float sumTimeByUserDescription(@Param(value = "userId") Integer userId, @Param(value = "description") String description);
}
