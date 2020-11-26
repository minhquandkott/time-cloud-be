package com.ces.intern.apitimecloud.service;


import com.ces.intern.apitimecloud.dto.DiscussionDTO;
import com.ces.intern.apitimecloud.http.request.DiscussionRequest;

import java.util.List;

public interface DiscussionService {

    DiscussionDTO create(DiscussionRequest input) throws Exception;

    DiscussionDTO update(DiscussionRequest input, Integer discussionId, Integer userId) throws Exception;

    DiscussionDTO updateType(Integer discussionId, Integer newType, Integer modifiedBy);

    void delete(Integer discussionId);

    List<DiscussionDTO> getAllByProjectId(Integer projectId, Integer limit, Integer page, String sortBy, String order);

    List<DiscussionDTO> getAllByProjectIdAndType(Integer projectId, Integer type,Integer limit, Integer page, String sortBy, String order);

    List<DiscussionDTO> getAllByUserIdInProject(Integer userId, Integer limit, Integer page, Integer type);
}
