package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity,Integer> {

}
