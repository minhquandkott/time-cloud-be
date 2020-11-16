package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.InteractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InteractRepository extends JpaRepository<InteractEntity, InteractEntity.EmbedId> {

    List<InteractEntity> getAllByIdDiscussionId(Integer discussionId);

    List<InteractEntity> getAllByIdUserId(Integer userId);

    void deleteAllByIdDiscussionId(Integer discussionId);
}
