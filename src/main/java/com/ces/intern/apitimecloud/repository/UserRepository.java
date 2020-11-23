package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.ProjectUserEntity;
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

    @Query(value = "select * from public.user where user_id in (select user_id from task_user where task_id=:taskId)", nativeQuery = true)
    List<UserEntity> getUserByTaskId(@Param(value = "taskId") Integer taskId);

    @Query(value = "select distinct public.user.user_id,user_name,email,password,gender,address,phone_number,avatar,is_active\n" +
            "\t\t,public.user.create_at,public.user.created_by,public.user.modify_at,public.user.modified_by\n" +
            "from time join public.user on public.user.user_id = time.user_id where task_id = :taskId", nativeQuery = true)
    List<UserEntity> getUserDidDoingByTaskId(@Param(value = "taskId") Integer taskId);

    Integer countByEmail(String email);
}
