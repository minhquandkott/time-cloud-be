package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.ProjectUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjectUserRepository extends JpaRepository<ProjectUserEntity, ProjectUserEntity.EmbedId> {

}
