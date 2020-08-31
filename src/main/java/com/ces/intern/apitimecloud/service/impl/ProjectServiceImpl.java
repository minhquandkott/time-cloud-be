package com.ces.intern.apitimecloud.service.impl;

import com.ces.intern.apitimecloud.dto.ProjectDTO;
import com.ces.intern.apitimecloud.entity.CompanyEntity;
import com.ces.intern.apitimecloud.entity.ProjectEntity;
import com.ces.intern.apitimecloud.http.exception.NotFoundException;
import com.ces.intern.apitimecloud.repository.CompanyRepository;
import com.ces.intern.apitimecloud.repository.ProjectRepository;
import com.ces.intern.apitimecloud.service.ProjectService;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public ProjectDTO createProject(Integer companyId, ProjectDTO projectDTO) {

        CompanyEntity company= companyRepository.findById(companyId).
                orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + " with " +companyId ));

        ProjectEntity projectEntity = modelMapper.map(projectDTO, ProjectEntity.class);

        projectEntity.setCompany(company);
        projectEntity.setCreateBy(1);
        projectEntity.setCreatAt(new Date());
        projectEntity.setModifyAt(new Date());

        projectEntity = projectRepository.save(projectEntity);

        modelMapper.map(projectEntity,projectDTO);

        return projectDTO;
    }

    @Override
    public ProjectDTO getProject(Integer projectId) {
        ProjectEntity projectEntity = projectRepository.findById(projectId).
                orElseThrow(()-> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+" with "+projectId));
        ProjectDTO projectDTO = modelMapper.map(projectEntity,ProjectDTO.class);
        return  projectDTO;
    }

    @Override
    public List getAllProject() {
        List listProject = new ArrayList<>();
        projectRepository.findAll().forEach(listProject::add);
        Type listType = new TypeToken<List<ProjectDTO>>() {}.getType();

        List<ProjectDTO> projectDTOS = modelMapper.map(listProject,listType);
        projectDTOS.forEach(t  -> System.out.println(t.getName()));
        return projectDTOS;
    }

    @Override
    public ProjectDTO updateProject(Integer projectId, ProjectDTO projectDTO) {

        ProjectEntity projectEntity = projectRepository.findById(projectId).
                orElseThrow(()-> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+" with "+projectId));

        projectEntity.setModifyAt(new Date());
        projectEntity.setName(projectDTO.getName());
        projectEntity.setClientName(projectDTO.getClientName());

        projectEntity = projectRepository.save(projectEntity);

        modelMapper.map(projectEntity,projectDTO);

        return projectDTO;
    }

    @Override
    public void deleteProject(Integer[] projectIds) {
        for(Integer item:projectIds){
            if(projectRepository.existsById(item)) {
                projectRepository.deleteById(item);
            } else {
                throw new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+ " with "+item);
            }
        }
    }
}
