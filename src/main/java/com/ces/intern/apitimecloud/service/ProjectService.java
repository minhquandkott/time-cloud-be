package com.ces.intern.apitimecloud.service;

import com.ces.intern.apitimecloud.dto.ProjectDTO;
import com.ces.intern.apitimecloud.entity.ProjectEntity;

import java.util.List;


public interface ProjectService {

    ProjectDTO createProject(Integer companyId, ProjectDTO projectDTO, Integer userId);

    ProjectDTO getProject(Integer projectId);

    List getAllProject();

    ProjectDTO updateProject(Integer projectId, ProjectDTO projectDTO, Integer userId);

    void deleteProject(Integer projectId);

    List<ProjectDTO> getAllByCompanyId(Integer companyId);

    List<ProjectDTO> getAllByUserId(Integer userId);

    void addUserToProject(Integer userId, Integer projectId);

    List<ProjectDTO> getAllByUserIdOOrderByTaskCount(Integer userId);
}
