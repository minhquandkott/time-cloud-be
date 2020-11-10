package com.ces.intern.apitimecloud.controller;

import com.ces.intern.apitimecloud.dto.CommentDTO;
import com.ces.intern.apitimecloud.http.exception.BadRequestException;
import com.ces.intern.apitimecloud.http.request.CommentRequest;
import com.ces.intern.apitimecloud.service.CommentService;
import com.ces.intern.apitimecloud.service.impl.CommentServiceImpl;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import com.ces.intern.apitimecloud.util.ResponseMessage;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    CommentController(CommentServiceImpl commentService){
        this.commentService = commentService;
    }

    @PostMapping("")
    private CommentDTO create(@RequestBody CommentRequest commentRequest){
        if(commentRequest.getContent().isEmpty()) throw new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.name());
        return commentService.create(commentRequest);
    }

    @PutMapping("/{commentId}")
    private CommentDTO update(@RequestBody CommentRequest commentRequest,
                              @PathVariable("commentId") Integer commentId,
                              @RequestHeader("userId") Integer userId){
        if(commentRequest.getContent().isEmpty()) throw new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.name());
        return commentService.update(commentRequest,commentId,userId);
    }

    @DeleteMapping("/{commentId}")
    private String delete( @PathVariable("commentId") Integer commentId){
        commentService.delete(commentId);
        return ResponseMessage.DELETE_SUCCESS;
    }
}
