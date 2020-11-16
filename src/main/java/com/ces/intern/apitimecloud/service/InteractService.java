package com.ces.intern.apitimecloud.service;

import com.ces.intern.apitimecloud.dto.InteractDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface InteractService {
    InteractDTO create(Integer userId, Integer discussionId);
    void delete(Integer userId, Integer discussionId);
    List<InteractDTO> getAllByDiscussionId(Integer discussionId);
    List<InteractDTO> getAllByUserId(Integer userId);
}
