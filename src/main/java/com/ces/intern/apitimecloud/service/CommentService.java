package com.ces.intern.apitimecloud.service;


import com.ces.intern.apitimecloud.dto.CommentDTO;
import com.ces.intern.apitimecloud.http.request.CommentRequest;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface CommentService {

    List<CommentDTO> getAllCommentByDiscussionId(Integer discussionId);

    CommentDTO create(CommentRequest input);

    CommentDTO update(CommentRequest input, Integer commentId, Integer userId);

    void delete(Integer commentId);


}
