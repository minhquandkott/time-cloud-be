package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Integer>
{
    List<TaskEntity> getAllByProjectId(Integer projectId);
}
