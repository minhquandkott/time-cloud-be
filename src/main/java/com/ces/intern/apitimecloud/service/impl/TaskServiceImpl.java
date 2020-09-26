package com.ces.intern.apitimecloud.service.impl;

import com.ces.intern.apitimecloud.dto.TaskDTO;
import com.ces.intern.apitimecloud.entity.ProjectEntity;
import com.ces.intern.apitimecloud.entity.TaskEntity;
import com.ces.intern.apitimecloud.http.exception.NotFoundException;
import com.ces.intern.apitimecloud.repository.ProjectRepository;
import com.ces.intern.apitimecloud.repository.TaskRepository;
import com.ces.intern.apitimecloud.service.TaskService;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private ProjectRepository projectRepository;
    private ModelMapper modelMapper;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository, ModelMapper modelMapper){
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
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

        if(taskEntities.size()==0) throw new NotFoundException
                (ExceptionMessage.NOT_FOUND_RECORD.getMessage()+ " with "+projectId);

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
    public void deleteTask(Integer[] ids) {
        
        for(Integer item:ids){
            if(taskRepository.existsById(item)) {
                taskRepository.deleteById(item);
            } else {
                throw new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+ " with "+item);
            }
        }
    }

    @Override
    public void addUserToTask(Integer userId, Integer taskId) {
        taskRepository.addUserToTask(userId, taskId);
    }
}
