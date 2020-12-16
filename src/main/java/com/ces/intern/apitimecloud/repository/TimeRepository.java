package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.TimeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeRepository extends JpaRepository<TimeEntity, Integer> {

    List<TimeEntity> getAllByUserId(Integer userId);
    List<TimeEntity> getAllByTaskId(Integer taskId);
    List<TimeEntity> getAllByDescription(String description);
    Page<TimeEntity> findAllByUserId(Integer userId, Pageable pageable);
    void deleteByTaskId (Integer taskId);

    @Query(value = "select extract(epoch from sum(end_time - start_time)) as totalTime from time where task_id =:taskId", nativeQuery = true)
    Float sumTimeByTaskId(@Param(value = "taskId") Integer taskId);

    @Query(value = "select extract(epoch from sum(end_time - start_time)) as totalTime from time where user_id =:userId", nativeQuery = true)
    Float sumTimeByUserId(@Param(value = "userId") Integer userId);

    @Query(value = "select * from time where user_id = :userId and to_char(create_at, 'dd-MM-yyyy') = :date", nativeQuery = true)
    List<TimeEntity> getAllByUserIdAndTime(@Param(value="userId")Integer userId, @Param(value = "date") String date);

    @Query(value = "select extract(epoch from sum(end_time - start_time)) as totalTime from time where task_id in (select task_id from task where project_id =:projectId)", nativeQuery = true)
    Float sumTimeByProjectId(@Param(value = "projectId") Integer projectId);

    @Query(value = "select extract(epoch from sum(end_time-start_time)) as totalTime from time where user_id = :userId and task_id = :taskId",nativeQuery = true)
    Float sumTimeByUserTask(@Param(value = "userId") Integer userId, @Param(value = "taskId") Integer taskId);

    @Query(value = "select extract(epoch from sum(end_time-start_time)) as totalTime from time join task on time.task_id = task.task_id where user_id = :userId and project_id = :projectId",nativeQuery = true)
    Float sumTimeByUserProject(@Param(value = "userId") Integer userId, @Param(value = "projectId") Integer projectId);

    @Query(value = "select extract(epoch from sum(end_time-start_time)) as totalTime from time join task on time.task_id = task.task_id where user_id = :userId and description = :description and project_id = :projectId",nativeQuery = true)
    Float sumTimeByUserDescription(@Param(value = "userId") Integer userId,@Param(value = "projectId") Integer projectId, @Param(value = "description") String description);

    @Query(value = "select extract(epoch from sum(end_time-start_time)) as totalTime from time join task on time.task_id = task.task_id where  user_id = :userId and end_time >= TO_TIMESTAMP( :dateStart, 'yyyy-mm-dd' ) and end_time < TO_TIMESTAMP( :dateEnd, 'yyyy-mm-dd')",nativeQuery = true)
    Float sumTimeByDayOfUser(@Param(value = "userId") Integer userId, @Param(value = "dateStart") String dateStart, @Param(value = "dateEnd") String dateEnd);

    @Query(value = "select extract(epoch from sum(end_time-start_time)) as totalTime from time join task on time.task_id = task.task_id where  project_id = :projectId and end_time >= TO_TIMESTAMP( :dateStart, 'yyyy-mm-dd' ) and end_time < TO_TIMESTAMP( :dateEnd, 'yyyy-mm-dd')",nativeQuery = true)
    Float sumTimeByDayOfProject(@Param(value = "projectId") Integer projectId, @Param(value = "dateStart") String dateStart, @Param(value = "dateEnd") String dateEnd);

}
