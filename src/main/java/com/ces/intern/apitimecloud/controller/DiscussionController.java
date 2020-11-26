package com.ces.intern.apitimecloud.controller;

import com.ces.intern.apitimecloud.dto.CommentDTO;
import com.ces.intern.apitimecloud.dto.DiscussionDTO;
import com.ces.intern.apitimecloud.dto.InteractDTO;
import com.ces.intern.apitimecloud.http.exception.BadRequestException;
import com.ces.intern.apitimecloud.http.request.DiscussionRequest;
import com.ces.intern.apitimecloud.service.CommentService;
import com.ces.intern.apitimecloud.service.DiscussionService;
import com.ces.intern.apitimecloud.service.InteractService;
import com.ces.intern.apitimecloud.service.impl.CommentServiceImpl;
import com.ces.intern.apitimecloud.service.impl.DiscussionServiceImpl;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import com.ces.intern.apitimecloud.util.ResponseMessage;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/discussions")
public class DiscussionController {

    private final DiscussionService discussionService;
    private final CommentService commentService;
    private final ModelMapper modelMapper;
    private final InteractService interactService;

    DiscussionController(DiscussionServiceImpl discussionService,
                         CommentServiceImpl commentService,
                         ModelMapper modelMapper,
                         InteractService interactService
                         ){
        this.discussionService = discussionService;
        this.commentService = commentService;
        this.modelMapper = modelMapper;
        this.interactService = interactService;
    }

    @PostMapping("")
    private DiscussionDTO create(@RequestBody DiscussionRequest discussionRequest) throws Exception {
        if(discussionRequest.getContent().isEmpty()) throw new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.name());
        return discussionService.create(discussionRequest);
    }



    @PutMapping("/{discussionId}")
    private DiscussionDTO update(@RequestBody DiscussionRequest discussionRequest,
                                 @PathVariable("discussionId") Integer discussionId,
                                 @RequestHeader("userId") Integer userId) throws Exception {
        return discussionService.update(discussionRequest, discussionId, userId);
    }

    @PutMapping("/{discussionId}/type/{type}")
    private DiscussionDTO updateType(@PathVariable("type")Integer type,
                                 @PathVariable("discussionId") Integer discussionId,
                                 @RequestHeader("userId") Integer userId) throws Exception {
        return discussionService.updateType(discussionId, type, userId);
    }

    @DeleteMapping(value = "/{discussionId}")
    private String delete(@PathVariable("discussionId")Integer discussionId){
        discussionService.delete(discussionId);
        return ResponseMessage.DELETE_SUCCESS;
    }

    @GetMapping("/{discussionId}/comments")
    private List<CommentDTO> getAllCommentByDiscussionId(@PathVariable("discussionId")Integer discussionId){
        if(discussionId == null) throw new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.getMessage() + "discussionId");
        return commentService.getAllCommentByDiscussionId(discussionId);
    }

    @GetMapping("/{discussionId}/interacts")
    private List<InteractDTO> getAllInteractByDiscussionId(@PathVariable("discussionId")Integer discussionId){
        if(discussionId == null) throw new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.getMessage() + "discussionId");
        return interactService.getAllByDiscussionId(discussionId);
    }

}
