package com.ces.intern.apitimecloud.service.impl;

import com.ces.intern.apitimecloud.dto.CommentDTO;
import com.ces.intern.apitimecloud.http.request.CommentRequest;
import com.ces.intern.apitimecloud.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Override
    public List<CommentDTO> getAllCommentByDiscussionId(Integer discussionId) {
        return null;
    }

    @Override
    public CommentDTO create(CommentRequest input) {
        return null;
    }

    @Override
    public CommentDTO update(CommentRequest input) {
        return null;
    }


    @Override
    public void delete(Integer commentId) {

    }
}
