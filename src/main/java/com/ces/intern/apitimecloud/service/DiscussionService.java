package com.ces.intern.apitimecloud.service;


import com.ces.intern.apitimecloud.dto.DiscussionDTO;
import com.ces.intern.apitimecloud.http.request.DiscussionRequest;

import java.util.List;

public interface DiscussionService {

    DiscussionDTO create(DiscussionRequest input);

    DiscussionDTO update(DiscussionRequest input, Integer discussionId, Integer userId);

    void delete(Integer discussionId);

    List<DiscussionDTO> getAllByProjectId(Integer projectId);

    List<DiscussionDTO> getAllByProjectIdAndType(Integer projectId, Integer type);
}
