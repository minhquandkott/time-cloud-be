package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity,Integer> {

    List<ProjectEntity> getAllByCompanyId(Integer companyId);

    @Modifying
    @Query(value = "delete from project_user where project_id =:projectId", nativeQuery = true)
    void deleteAllProjectUser(@Param("projectId") Integer projectId);

    @Modifying
    @Query(value = "delete from project_user where project_id =:projectId and user_id =:userId", nativeQuery = true)
    void deleteUserOfProject(@Param("projectId") Integer projectId, @Param("userId") Integer userId);

    @Query(value = "select * from project where project_id in(select project_id from public.project_user where project_user.user_id =:userId)",
    nativeQuery = true)
    List<ProjectEntity> getAllByUserId(@Param("userId") Integer userId);

    @Query(value = "select * from project where project_id in (select p.project_id  as c from project as p left join task as t on p.project_id = t.project_id where p.project_id in (select project_id from project_user as pu where pu.user_id = :userId) group by p.project_id order by count(p.project_id) desc)",
            nativeQuery = true)
    List<ProjectEntity> getAllByUserIdOOrderByTaskCount(@Param("userId") Integer userId);

    @Modifying
    @Query(value = "insert into public.project_user(user_id, project_id) values(:userId, :projectId)", nativeQuery = true)
    void addUserToProject(@Param("userId") Integer userId, @Param("projectId")Integer projectId);

}
