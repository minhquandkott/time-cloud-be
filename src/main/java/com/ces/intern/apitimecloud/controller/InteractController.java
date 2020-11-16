package com.ces.intern.apitimecloud.controller;

import com.ces.intern.apitimecloud.dto.InteractDTO;
import com.ces.intern.apitimecloud.entity.InteractEntity;
import com.ces.intern.apitimecloud.http.request.InteractRequest;
import com.ces.intern.apitimecloud.service.InteractService;
import com.ces.intern.apitimecloud.util.ResponseMessage;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interacts")
public class InteractController {

    private final InteractService interactService;


    public InteractController(InteractService interactService){
        this.interactService = interactService;
    }

    @PostMapping("")
    public InteractDTO create(@RequestBody InteractRequest interactRequest){
        return interactService.create(interactRequest.getUserId(), interactRequest.getDiscussionId());
    }

    @PostMapping("/delete")
    public String delete(@RequestBody InteractRequest interactRequest){
        interactService.delete(interactRequest.getUserId(), interactRequest.getDiscussionId());
        return ResponseMessage.DELETE_SUCCESS;
    }
}
