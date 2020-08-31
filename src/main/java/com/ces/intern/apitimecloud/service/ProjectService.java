package com.ces.intern.apitimecloud.service;

import com.ces.intern.apitimecloud.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {

    public ProjectDTO createProject(Integer companyId, ProjectDTO projectDTO);

    public ProjectDTO getProject(Integer projectId);

    public List getAllProject();

    public ProjectDTO updateProject(Integer projectId, ProjectDTO projectDTO);

    public void deleteProject(Integer[] projectIds);
}
