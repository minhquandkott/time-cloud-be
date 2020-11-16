package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.CommentEntity;
import com.ces.intern.apitimecloud.entity.DiscussionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    List<CommentEntity> getAllByDiscussionId(Integer discussionId);
    void deleteAllByDiscussionId(Integer discussionId);
}
