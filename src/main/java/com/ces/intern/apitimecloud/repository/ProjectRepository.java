package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity,Integer> {

    List<ProjectEntity> getAllByCompanyId(Integer companyId);

    @Query(value = "select * from project where project_id in(select project_id from public.project_user where project_user.user_id =:userId)",
    nativeQuery = true)
    List<ProjectEntity> getAllByUserId(@Param("userId") Integer userId);
}
