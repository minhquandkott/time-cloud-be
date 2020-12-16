package com.ces.intern.apitimecloud.service.impl;


import com.ces.intern.apitimecloud.dto.TimeDTO;
import com.ces.intern.apitimecloud.entity.TaskEntity;
import com.ces.intern.apitimecloud.entity.TimeEntity;
import com.ces.intern.apitimecloud.entity.UserEntity;
import com.ces.intern.apitimecloud.http.exception.BadRequestException;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
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
    public TimeResponse update(TimeRequest timeRequest, Integer modifiedBy, Integer timeId) {
        TimeEntity timeEntity= timeRepository
                .findById(timeId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD + " Time with id : "+ timeId));
        if(!timeEntity.getTask().getId().equals(timeRequest.getTaskId())){
            TaskEntity taskEntity = taskRepository.findById(timeRequest.getTaskId())
                    .orElseThrow(() ->
                            new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.name() + " task with id " + timeRequest.getTaskId()));
            timeEntity.setTask(taskEntity);
        }
        if(!timeEntity.getUser().getId().equals(timeRequest.getUserId())){
            UserEntity userEntity = userRepository.findById(timeRequest.getUserId())
                    .orElseThrow(() ->
                            new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.name() + " user with id " + timeRequest.getUserId()));
            timeEntity.setUser(userEntity);
        }
        timeEntity.setEndTime(new Date(timeRequest.getMileSecondEndTime()));
        timeEntity.setStartTime(new Date(timeRequest.getMileSecondStartTime()));
        timeEntity.setDescription(timeRequest.getDescription());
        timeEntity.setModifiedBy(modifiedBy);
        timeEntity.setModifyAt(new Date());

        return modelMapper.map(timeRepository.save(timeEntity), TimeResponse.class);
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

        return timeRepository.sumTimeByUserId(userId);
    }

    @Override
    public Float sumTimeByTaskId(Integer taskId) {

        return timeRepository.sumTimeByTaskId(taskId);
    }

    @Override
    public Float sumTimeByProjectId(Integer projectId) {
        return timeRepository.sumTimeByProjectId(projectId);
    }

    @Override
    public Float sumTimeByUserTask(Integer userId, Integer taskId) {
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

        return timeRepository.sumTimeByUserProject(userId,projectId);
    }

    @Override
    public Float sumTimeByUserDescription(Integer userId, Integer projectId, String description) {

        return timeRepository.sumTimeByUserDescription(userId,projectId,description);
    }

    @Override
    public Float sumTimeByDayOfUser(Integer userId, String dateStart, String dateEnd) {
        Float result = timeRepository.sumTimeByDayOfUser(userId,dateStart,dateEnd);
        if(result == null){
            return new Float(0);
        }
        return result;
    }

    @Override
    public Float sumTimeByWeekOfUser(Integer userId, String date) throws ParseException {

        String firstDayOfWeek = Utils.toFirstDayOfWeek(date);
        return sumTimeByDayOfUser(userId,firstDayOfWeek,Utils.toNumbersOfDay(firstDayOfWeek,7));
    }

    @Override
    public Float sumTimeByDayOfProject(Integer projectId, String dateStart, String dateEnd) {

        return timeRepository.sumTimeByDayOfProject(projectId,dateStart,dateEnd);
    }

    @Override
    public List<Float> getAllSumTimesByDayOfWeekOfProject(Integer projectId, String date) throws ParseException {

        List<Float> listTimes = new ArrayList<>();
        String dayOfWeek = Utils.toFirstDayOfWeek(date);
        Float timeOfDay = 0f;
        for(int i = 1; i <= 7; i++){
            timeOfDay = sumTimeByDayOfProject(projectId,dayOfWeek,Utils.toNumbersOfDay(dayOfWeek,1));
            listTimes.add(timeOfDay);
            dayOfWeek = Utils.toNumbersOfDay(dayOfWeek,1);
        }
        return listTimes;
    }

    @Override
    public List<TimeDTO> getAllTimeByUserIdAndPageable(Integer userId, Integer limit, Integer page, String sortBy,String order) {
        Pageable p = PageRequest.of(page, limit);

        if(!sortBy.isEmpty()){
            if("ASC".equals(order)){
                p = PageRequest.of(page, limit, Sort.by(sortBy).ascending());
            }else if("DESC".equals(order)){
                p = PageRequest.of(page, limit, Sort.by(sortBy).descending());
            }else{
                throw new BadRequestException(ExceptionMessage.FIELD_NOT_CORRECT.getMessage() + " order (DESC, ASC)");
            }
        }
        List<TimeEntity> timeEntities = timeRepository.findAllByUserId(userId, p).get().collect(Collectors.toList());
        return timeEntities
                .stream()
                .map(timeEntity -> modelMapper.map(timeEntity, TimeDTO.class))
                .collect(Collectors.toList());

    }


}
