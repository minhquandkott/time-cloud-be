package com.ces.intern.apitimecloud.service.impl;

import com.ces.intern.apitimecloud.dto.ProjectDTO;
import com.ces.intern.apitimecloud.dto.ProjectUserDTO;
import com.ces.intern.apitimecloud.entity.*;
import com.ces.intern.apitimecloud.http.exception.NotFoundException;
import com.ces.intern.apitimecloud.http.request.ProjectUserRequest;
import com.ces.intern.apitimecloud.repository.*;
import com.ces.intern.apitimecloud.service.ProjectService;
import com.ces.intern.apitimecloud.service.TaskService;
import com.ces.intern.apitimecloud.service.UserService;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskService taskService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ProjectUserRepository projectUserRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,
                              CompanyRepository companyRepository,
                              TaskService taskService,
                              UserService userService,
                              ModelMapper modelMapper,
                              ProjectUserRepository projectUserRepository,
                              UserRepository userRepository,
                              TaskRepository taskRepository
                             ){
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskService = taskService;
        this.userService = userService;
        this.projectUserRepository = projectUserRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    @Transactional
    public ProjectDTO createProject(Integer companyId, ProjectDTO projectDTO, Integer userId) {
        CompanyEntity company= companyRepository.findById(companyId).
                orElseThrow(()
                        -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + " with " +companyId ));

        ProjectEntity projectEntity = modelMapper.map(projectDTO, ProjectEntity.class);

        projectEntity.setCompany(company);
        Date date = new Date();
        projectEntity.setBasicInfo(date, userId, date, userId);
        projectEntity.setDone(false);
        projectEntity = projectRepository.save(projectEntity);
        this.addUserToProject(userId, projectEntity.getId());

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
        projectEntity.setColor(projectDTO.getColor());

        projectEntity.setModifyAt(new Date());
        projectEntity.setModifiedBy(userId);

        projectEntity = projectRepository.save(projectEntity);

        return modelMapper.map(projectEntity,ProjectDTO.class);
    }

    @Override
    public void deleteAllProjectUser(Integer projectId) {

        projectRepository.deleteAllProjectUser(projectId);
    }

    @Override
    public boolean checkProjectAvailable(Integer projectId) {
        ProjectEntity projectEntity =  projectRepository.findById(projectId).orElseThrow(() ->
                new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+" with projectId "+ projectId));
        if(projectEntity.getDone()) {
            return true;
        }
        return false;
    }

    @Override
    public List<ProjectUserDTO> getAllUserByIsDoing(Integer projectId, boolean isDoing) {
        projectRepository.findById(projectId).orElseThrow(() ->
                new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+" with projectId "+ projectId));
        List<ProjectUserEntity> projectUserEntities = projectUserRepository.getAllByEmbedId_ProjectIdAndIsDoing(projectId,isDoing);
        return projectUserEntities
                .stream()
                .map(projectUser -> modelMapper.map(projectUser, ProjectUserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProjectUserDTO editProjectUser(Integer projectId, Integer userId, ProjectUserRequest projectUserRequest) {

        ProjectUserEntity projectUserEntity = projectUserRepository
                .findById(new ProjectUserEntity.EmbedId(projectId, userId))
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+ " with projectId and userId : " +projectId + " " + userId));
        if(projectUserRequest.getIsDoing() != null){
            projectUserEntity.setIsDoing(projectUserRequest.getIsDoing());
        }if(projectUserRequest.getIsShow() != null){
            projectUserEntity.setIsShow(projectUserRequest.getIsShow());
        }if(projectUserRequest.getIndex() != null){
            projectUserEntity.setIndex(projectUserRequest.getIndex());
        }
        projectUserRepository.save(projectUserEntity);
        return modelMapper.map(projectUserEntity, ProjectUserDTO.class);
    }


    @Override
    @Transactional
    public void deleteUserOfProject(Integer projectId, Integer userId) {
            ProjectUserEntity projectUserEntity = projectUserRepository.getByEmbedIdProjectIdAndEmbedIdUserId(projectId,userId);
            if(projectUserEntity == null)
                throw new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + " with " + userId +" or "+projectId);

            taskService.deleteUserOfAllTaskOfProject(projectId,userId);

            projectUserEntity.setIsDoing(false);
            projectUserEntity.setIndex(-1);
            projectUserRepository.save(projectUserEntity);
    }


    @Override
    @Transactional
    public ProjectDTO changeStatusProject(Integer projectId, Boolean done) {
        ProjectEntity projectEntity = projectRepository.findById(projectId).
                orElseThrow(()-> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+" with "+projectId));

        projectEntity.setDone(done);

        projectRepository.save(projectEntity);

        return modelMapper.map(projectEntity,ProjectDTO.class);
    }


    @Override
    public List<ProjectDTO> getAllByCompanyId(Integer companyId) {
        List<ProjectEntity> projectEntities = projectRepository.getAllByCompanyId(companyId);

        return projectEntities.stream()
                .map(project  -> modelMapper.map(project, ProjectDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public List<ProjectDTO> getAllStillDoingByCompanyId(Integer companyId) {
        List<ProjectDTO> listProjects = getAllByCompanyId(companyId);
        List<ProjectDTO> listProjectNews = new ArrayList<>();
        for(int i = 0; i < listProjects.size(); i++){
            if(!checkProjectAvailable(listProjects.get(i).getId())){
                listProjectNews.add(listProjects.get(i));
            }
        }
        return  listProjectNews;
    }


    @Override
    public List<ProjectDTO> getAllByUserId(Integer userId) {

        List<ProjectEntity> projectEntities = projectRepository.getAllByUserId(userId);

        if(projectEntities.isEmpty()) throw new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()
                + " with " + userId);

        return projectEntities.stream()
                .map(project  -> modelMapper.map(project, ProjectDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> getAllByUserIdAndIsDoing(Integer userId) {
        UserEntity userEntity = userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + "with user " + userId));

        List<ProjectEntity> projectEntities = projectRepository.getAllByUserIdAndIsDoing(userId);
        return projectEntities.stream().map(project -> modelMapper.map(project,ProjectDTO.class) )
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProjectUserDTO addUserToProject(Integer userId, Integer projectId) {
        Optional<ProjectUserEntity> optional = projectUserRepository.findById(new ProjectUserEntity.EmbedId(projectId, userId));
        ProjectUserEntity projectUserEntity;
        int length  = projectUserRepository.countByIsDoingAndEmbedId_UserId(true, userId);

        if(optional.isPresent()){
            projectUserEntity = optional.get();
            projectUserEntity.setIsDoing(true);
        }else{
            UserEntity userEntity = userRepository
                    .findById(userId)
                    .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + "with user " + userId));

            ProjectEntity projectEntity = projectRepository
                    .findById(projectId)
                    .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + "with project " + projectId));

            projectUserEntity = new ProjectUserEntity();

            projectUserEntity.setProject(projectEntity);
            projectUserEntity.setUser(userEntity);
            projectUserEntity.setIsDoing(true);
        }
        projectUserEntity.setIndex(length);
        projectUserRepository.save(projectUserEntity);
        return modelMapper.map(projectUserEntity,ProjectUserDTO.class);

    }

    @Override
    public List<ProjectDTO> getAllByUserIdOOrderByTaskCount(Integer userId) {
        List<ProjectEntity> projectEntities = projectRepository.getAllByUserIdOOrderByTaskCount(userId);

        if(projectEntities.isEmpty()) throw new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()
                + " with " + userId);

        return projectEntities.stream()
                .map(project  -> modelMapper.map(project, ProjectDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectUserDTO> getAllByProjectUserId(Integer userId) {
        UserEntity userEntity = userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + "with user " + userId));
        List<ProjectUserEntity> projectUserEntities = projectUserRepository.getAllByEmbedIdUserId(userId);
        return projectUserEntities.stream()
                .map(projectUser -> modelMapper.map(projectUser,ProjectUserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectUserDTO> getAllByUserIdAndNotDone(Integer userId) {
        UserEntity userEntity = userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + "with user " + userId));
        List<ProjectUserEntity> projectUserEntities = projectUserRepository.getAllByUserIdAndNotDone(userId);
        return projectUserEntities.stream()
                .map(projectUser -> modelMapper.map(projectUser,ProjectUserDTO.class))
                .collect(Collectors.toList());
    }

}
