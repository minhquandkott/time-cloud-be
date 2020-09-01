package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity,Integer> {

    List<ProjectEntity> getAllByCompanyId(Integer companyId);
}
