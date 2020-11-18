package com.ces.intern.apitimecloud.service.impl;

import com.ces.intern.apitimecloud.dto.TaskDTO;
import com.ces.intern.apitimecloud.entity.ProjectEntity;
import com.ces.intern.apitimecloud.entity.TaskEntity;
import com.ces.intern.apitimecloud.entity.TaskUserEntity;
import com.ces.intern.apitimecloud.entity.UserEntity;
import com.ces.intern.apitimecloud.http.exception.NotFoundException;
import com.ces.intern.apitimecloud.repository.ProjectRepository;
import com.ces.intern.apitimecloud.repository.TaskRepository;
import com.ces.intern.apitimecloud.repository.TimeRepository;
import com.ces.intern.apitimecloud.repository.UserRepository;
import com.ces.intern.apitimecloud.service.TaskService;
import com.ces.intern.apitimecloud.service.TimeService;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TimeRepository timeRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final TimeService timeService;
    @PersistenceContext
    private EntityManager em;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository,
                           ProjectRepository projectRepository,
                           ModelMapper modelMapper,
                           TimeService timeService,TimeRepository timeRepository,
                           UserRepository userRepository){
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.timeRepository = timeRepository;
        this.modelMapper = modelMapper;
        this.timeService = timeService;
    }


    @Override
    @Transactional
    public TaskDTO createTask(Integer projectId, TaskDTO taskDTO, Integer userId) {

        ProjectEntity projectEntity = projectRepository.findById(projectId).
                orElseThrow(()->new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+" with "+ projectId));

        TaskEntity taskEntity = modelMapper.map(taskDTO,TaskEntity.class);

        Date date = new Date();
        taskEntity.setBasicInfo(date, userId, date, userId);
        taskEntity.setProject(projectEntity);

        taskEntity = taskRepository.save(taskEntity);

        return  modelMapper.map(taskEntity,TaskDTO.class);
    }


    @Override
    public TaskDTO getTask(Integer taskId) {

        TaskEntity taskEntity = taskRepository.findById(taskId).
                orElseThrow(()-> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD+" with "+taskId));

        return modelMapper.map(taskEntity,TaskDTO.class);
    }


    @Override
    public List getAllTaskByProject(Integer projectId) {

        List<TaskEntity> taskEntities = taskRepository.getAllByProjectId(projectId);


        return taskEntities.stream().map(task -> modelMapper.map(task,TaskDTO.class)).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public TaskDTO updateTask(Integer projectId, TaskDTO taskDTO, Integer userId) {

        TaskEntity taskEntity = taskRepository.findById(projectId).
                orElseThrow(()->new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD+" with "+ projectId));

        taskEntity.setName(taskDTO.getName());

        taskEntity.setModifiedBy(userId);

        taskEntity.setModifyAt(new Date());

        taskEntity = taskRepository.save(taskEntity);

        return modelMapper.map(taskEntity,TaskDTO.class);
    }

    @Override
    @Transactional
    public void deleteTask(Integer taskId) {

        if(taskRepository.existsById(taskId)) {
            timeService.deleteAllTimeByTaskId(taskId);
            taskRepository.deleteUsersOfTask(taskId);
            taskRepository.deleteById(taskId);
        } else {
            throw new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+ " with "+taskId);
        }
    }

    @Override
    @Transactional
    public void deleteUserOfTask(Integer taskId, Integer userId) {
        taskRepository.deleteUserOfTask(taskId, userId);
    }


    @Override
    @Transactional
    public void addUserToTask(Integer userId, Integer taskId) {
        taskRepository.addUserToTask(userId, taskId);
    }

    @Override
    public Float sumTimeByTask(Integer taskId) {
        return timeRepository.sumTimeByTaskId(taskId);
    }

    @Override
    public List getAllTaskByUser(Integer userId) {
        List<TaskEntity> taskEntities = taskRepository.getAllByUserId(userId);
        taskEntities.stream().sorted((o1, o2) -> (int)(o1.getCreateAt().getTime() - o2.getCreateAt().getTime()));

        return taskEntities.stream().map(task -> modelMapper.map(task,TaskDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getAllByUserIdAndProjectId(Integer userId, Integer projectId){
        List<TaskEntity> taskEntities = taskRepository.getAllByUserIdAndProjectId(userId, projectId);

        return taskEntities.stream().map(task -> modelMapper.map(task, TaskDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getAllDidDoingByUserIdAndProjectId(Integer userId, Integer projectId) {
        List<TaskEntity> taskEntities = new ArrayList<>();
        List<TaskEntity> taskDidEntities = taskRepository.getAllDidDoingByUserAndProjectId(userId, projectId);
        List<TaskEntity> taskDoingEntities = taskRepository.getAllByUserIdAndProjectId(userId, projectId);

        for(int i = 0; i < taskDidEntities.size(); i++){
            if(!taskDoingEntities.contains(taskDidEntities.get(i))){
                taskEntities.add(taskDidEntities.get(i));
            }
        }

        return taskEntities.stream().map(task -> modelMapper.map(task, TaskDTO.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteUserOfAllTaskOfProject(Integer projectId, Integer userId) {
        ProjectEntity projectEntity = projectRepository.findById(projectId).
                orElseThrow(()->new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD+" with "+ projectId));

        UserEntity userEntity = userRepository.findById(userId).
                orElseThrow(()->new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD+" with "+ userId));

        taskRepository.deleteUserOfAllTaskOfProject(projectId,userId);
    }

    @Override
    @Transactional
    public void deleteUsersOfAllTaskOfProject(Integer projectId) {
        ProjectEntity projectEntity = projectRepository.findById(projectId).
                orElseThrow(()->new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD+" with "+ projectId));

        taskRepository.deleteUsersOfAllTaskOfProject(projectId);
    }

    @Override
    public List<TaskUserEntity> getAllTaskUsersByProjectIdAndUserId(Integer projectId, Integer userId) {
        return em.createNamedQuery("getTaskUserInProject")
                .setParameter(1, userId)
                .setParameter(2, projectId)
                .getResultList();
    }

}

