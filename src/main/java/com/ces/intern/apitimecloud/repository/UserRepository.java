package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmail(String email);

    @Query(value = "select * from public.user where user_id in (select user_id from project_user where project_id=:projectId)", nativeQuery = true)
    List<UserEntity> getUserByProjectId(@Param(value = "projectId") Integer projectId);

    @Query(value = "select * from public.user where user_id in (select user_id from task_user where task_id=:taskId)", nativeQuery = true)
    List<UserEntity> getUserByTaskId(@Param(value = "taskId") Integer taskId);

    Integer countByEmail(String email);
}
