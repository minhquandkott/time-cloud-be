package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.ProjectEntity;
import com.ces.intern.apitimecloud.entity.ProjectUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity,Integer> {

    List<ProjectEntity> getAllByCompanyId(Integer companyId);
    List<ProjectEntity> getAllByProjectManager_Id(Integer projectManagerId);

    @Modifying
    @Query(value = "update project_user set is_doing = false", nativeQuery = true)
    void deleteAllProjectUser(@Param("projectId") Integer projectId);

    @Query(value = "select * from project where project_id in(select project_id from public.project_user where project_user.user_id =:userId)",
    nativeQuery = true)
    List<ProjectEntity> getAllByUserId(@Param("userId") Integer userId);

    @Query(value = "select * from project where project_id in (select p.project_id  as c from project as p left join task as t on p.project_id = t.project_id where p.project_id in (select project_id from project_user as pu where pu.user_id = :userId) group by p.project_id order by count(p.project_id) desc)",
            nativeQuery = true)
    List<ProjectEntity> getAllByUserIdOOrderByTaskCount(@Param("userId") Integer userId);

    @Query(value = "select * from project where project_id in(select project_id from public.project_user where project_user.user_id =:userId and is_doing=true)",
            nativeQuery = true)
    List<ProjectEntity> getAllByUserIdAndIsDoing(@Param("userId") Integer userId);

    @Query(value = "select count(*) from task_user where task_id in (select task_id from task where project_id = :projectId)", nativeQuery = true)
    int checkProjectAvailable(@Param("projectId") Integer projectId);



}
