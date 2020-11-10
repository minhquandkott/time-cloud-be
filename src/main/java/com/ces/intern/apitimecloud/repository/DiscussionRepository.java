package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.DiscussionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussionRepository extends JpaRepository<DiscussionEntity, Integer> {

    List<DiscussionEntity> getAllByProjectId(Integer projectId);

    List<DiscussionEntity> getAllByProjectIdAndType(Integer projectId, Integer type);
}
