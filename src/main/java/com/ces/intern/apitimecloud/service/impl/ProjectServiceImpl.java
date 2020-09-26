package com.ces.intern.apitimecloud.service.impl;

import com.ces.intern.apitimecloud.dto.ProjectDTO;
import com.ces.intern.apitimecloud.dto.TaskDTO;
import com.ces.intern.apitimecloud.entity.CompanyEntity;
import com.ces.intern.apitimecloud.entity.BaseEntity;
import com.ces.intern.apitimecloud.entity.ProjectEntity;
import com.ces.intern.apitimecloud.http.exception.NotFoundException;
import com.ces.intern.apitimecloud.repository.CompanyRepository;
import com.ces.intern.apitimecloud.repository.ProjectRepository;
import com.ces.intern.apitimecloud.service.ProjectService;
import com.ces.intern.apitimecloud.service.TaskService;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final TaskService taskService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,
                              CompanyRepository companyRepository,
                              TaskService taskService,
                              ModelMapper modelMapper){
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }


    @Override
    public ProjectDTO createProject(Integer companyId, ProjectDTO projectDTO, Integer userId) {

        CompanyEntity company= companyRepository.findById(companyId).
                orElseThrow(()
                        -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + " with " +companyId ));

        ProjectEntity projectEntity = modelMapper.map(projectDTO, ProjectEntity.class);

        projectEntity.setCompany(company);

        Date date = new Date();

        projectEntity.setBasicInfo(date, userId, date, userId);

        projectEntity = projectRepository.save(projectEntity);

        return modelMapper.map(projectEntity,ProjectDTO.class);
    }


    @Override
    public ProjectDTO getProject(Integer projectId) {

        ProjectEntity projectEntity = projectRepository.findById(projectId).
                orElseThrow(()-> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+" with "+projectId));

        return modelMapper.map(projectEntity,ProjectDTO.class);
    }


    @Override
    public List getAllProject() {

        List listProject = new ArrayList<>();

        projectRepository.findAll().forEach(listProject::add);

        Type listType = new TypeToken<List<ProjectDTO>>() {}.getType();

        List<ProjectDTO> projectDTOS = modelMapper.map(listProject,listType);

        return projectDTOS;
    }


    @Override
    @Transactional
    public ProjectDTO updateProject(Integer projectId, ProjectDTO projectDTO, Integer userId) {

        ProjectEntity projectEntity = projectRepository.findById(projectId).
                orElseThrow(()-> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+" with "+projectId));

        projectEntity.setName(projectDTO.getName());
        projectEntity.setClientName(projectDTO.getClientName());

        projectEntity.setModifyAt(new Date());
        projectEntity.setModifiedBy(userId);

        projectEntity = projectRepository.save(projectEntity);

        return modelMapper.map(projectEntity,ProjectDTO.class);
    }


    @Override
    @Transactional
    public void deleteProject(Integer[] projectIds) {

        for(Integer item:projectIds){
            if(projectRepository.existsById(item)) {

                List<TaskDTO> list = taskService.getAllTaskByProject(item);

                Integer[] idArray = list.stream().map(itemOfList->itemOfList.getId()).toArray(Integer[]::new);

                taskService.deleteTask(idArray);

                projectRepository.deleteById(item);
            } else {
                throw new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+ " with "+item);
            }
        }
    }


    @Override
    public List<ProjectDTO> getAllByCompanyId(Integer companyId) {
        List<ProjectEntity> projectEntities = projectRepository.getAllByCompanyId(companyId);

        if(projectEntities.size() == 0) throw new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()
                                                                        + " with " + companyId);

        return projectEntities.stream()
                .map(project  -> modelMapper.map(project, ProjectDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public List<ProjectDTO> getAllByUserId(Integer userId) {

        List<ProjectEntity> projectEntities = projectRepository.getAllByUserId(userId);

        if(projectEntities.size() == 0) throw new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()
                + " with " + userId);

        return projectEntities.stream()
                .map(project  -> modelMapper.map(project, ProjectDTO.class))
                .collect(Collectors.toList());
    }
}
