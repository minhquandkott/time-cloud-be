package com.ces.intern.apitimecloud.service.impl;

import com.ces.intern.apitimecloud.dto.TimeDTO;
import com.ces.intern.apitimecloud.entity.BaseEntity;
import com.ces.intern.apitimecloud.entity.TaskEntity;
import com.ces.intern.apitimecloud.entity.TimeEntity;
import com.ces.intern.apitimecloud.entity.UserEntity;
import com.ces.intern.apitimecloud.http.exception.NotFoundException;
import com.ces.intern.apitimecloud.http.request.TimeRequest;
import com.ces.intern.apitimecloud.http.response.TimeResponse;
import com.ces.intern.apitimecloud.repository.ProjectRepository;
import com.ces.intern.apitimecloud.repository.TaskRepository;
import com.ces.intern.apitimecloud.repository.TimeRepository;
import com.ces.intern.apitimecloud.repository.UserRepository;
import com.ces.intern.apitimecloud.service.TimeService;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import com.ces.intern.apitimecloud.util.Utils;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeServiceImpl implements TimeService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TimeRepository timeRepository;
    private final ModelMapper modelMapper;
    private final ProjectRepository projectRepository;

    @Autowired
    public TimeServiceImpl( UserRepository userRepository,
                            TaskRepository taskRepository,
                            TimeRepository timeRepository,
                            ModelMapper modelMapper,
                            ProjectRepository projectRepository) {
        this.timeRepository = timeRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
        this.projectRepository = projectRepository;
    }

    @Override
    @Transactional
    public TimeResponse save(Integer userId, TimeRequest timeRequest, Integer taskId) {
        TypeMap<TimeRequest, TimeEntity> tm = modelMapper.typeMap(TimeRequest.class, TimeEntity.class);
        Converter<Long,Date> converter = (context) -> context.getSource() == null ? null : new Date(context.getSource());
        tm.addMappings(mapping ->{
            mapping.using(converter).map(TimeRequest::getMileSecondEndTime, TimeEntity::setEndTime);
            mapping.using(converter).map(TimeRequest::getMileSecondStartTime, TimeEntity::setStartTime);
        });

        TimeEntity timeEntity = tm.map(timeRequest);
        TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(() ->
                new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage())) ;

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()));

        timeEntity.setUser(userEntity);
        timeEntity.setTask(taskEntity);
        Date date = new Date();
        timeEntity.setBasicInfo(date, userId, date, userId);
        TimeEntity time = timeRepository.save(timeEntity);

        return modelMapper.map(time, TimeResponse.class);
    }



    @Override
    public TimeResponse find(Integer id) {

        TimeEntity timeEntity = timeRepository.findById(id).orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()));
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        return modelMapper.map(timeEntity, TimeResponse.class);
    }

    @Override
    @Transactional
    public void delete(Integer timeId) {
        TimeEntity timeEntity = timeRepository.findById(timeId)
                .orElseThrow(() -> new RuntimeException((ExceptionMessage.NOT_FOUND_RECORD.getMessage() + " with " + timeId)));
        timeRepository.delete(timeEntity);
    }

    @Override
    public List<TimeDTO> getTimesByUserId(Integer userId) {
        List<TimeEntity> times = timeRepository.getAllByUserId(userId);
        return times.stream()
                .map(time -> modelMapper.map(time, TimeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TimeDTO> getAllByUserIdAndDate(Integer userId, String date) {
        List<TimeEntity> times = timeRepository.getAllByUserIdAndTime(userId, date);
        return times.stream()
                .map(time -> modelMapper.map(time, TimeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TimeDTO> getAllByTaskId(Integer taskId) {
        List<TimeEntity> timeEntities = timeRepository.getAllByTaskId(taskId);
        return timeEntities
                .stream()
                .map(timeEntity -> modelMapper.map(timeEntities, TimeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Float sumTimeByUserId(Integer userId) {
        userRepository.findById(userId)
                .orElseThrow(() ->
                new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+ "with user" + userId) );

        return timeRepository.sumTimeByUserId(userId);
    }

    @Override
    public Float sumTimeByTaskId(Integer taskId) {
        taskRepository.findById(taskId)
                .orElseThrow(()
                        -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+ "with task" + taskId) );

        return timeRepository.sumTimeByTaskId(taskId);
    }

    @Override
    public Float sumTimeByProjectId(Integer projectId) {
        projectRepository.findById(projectId)
                .orElseThrow(()
                        -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+ "with task" + projectId) );

        return timeRepository.sumTimeByProjectId(projectId);
    }

    @Override
    public Float sumTimeByUserTask(Integer userId, Integer taskId) {
        userRepository.findById(userId)
                .orElseThrow(()
                        -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+ "with user" + userId) );
        taskRepository.findById(taskId)
                .orElseThrow(()
                        -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+ "with task" + taskId) );

        return timeRepository.sumTimeByUserTask(userId,taskId);
    }

    @Override
    public void deleteAllTimeByTaskId(Integer taskId) {
        taskRepository.findById(taskId)
                .orElseThrow(()
                -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+ "with task" + taskId) );
        timeRepository.deleteByTaskId(taskId);
    }

    @Override
    public Float sumTimeByUserProject(Integer userId, Integer projectId) {
        userRepository.findById(userId)
                .orElseThrow(()
                        -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+ "with user" + userId) );
        projectRepository.findById(projectId)
                .orElseThrow(()
                        -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+ "with task" + projectId));

        return timeRepository.sumTimeByUserProject(userId,projectId);
    }

    @Override
    public Float sumTimeByUserDescription(Integer userId, String description) {
        userRepository.findById(userId)
                .orElseThrow(()
                        -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+ "with user" + userId) );

        if(timeRepository.getAllByDescription(description).isEmpty()){
            throw new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+ "with description" + description);
        }

        return timeRepository.sumTimeByUserDescription(userId,description);
    }

    @Override
    public Float sumTimeByDayOfUser(Integer userId, String dateStart, String dateEnd) {
        userRepository.findById(userId)
                .orElseThrow(()
                        -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+ "with user" + userId) );
        return timeRepository.sumTimeByDayOfUser(userId,dateStart,dateEnd);
    }

    @Override
    public Float sumTimeByWeekOfUser(Integer userId, String date) throws ParseException {
        userRepository.findById(userId)
                .orElseThrow(()
                        -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+ "with user" + userId) );
        String firstDayOfWeek = Utils.toFirstDayOfWeek(date);
        return sumTimeByDayOfUser(userId,firstDayOfWeek,Utils.toNumbersOfDay(firstDayOfWeek,7));
    }
}
