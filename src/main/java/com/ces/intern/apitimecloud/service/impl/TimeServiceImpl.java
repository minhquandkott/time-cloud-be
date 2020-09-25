package com.ces.intern.apitimecloud.service.impl;

import com.ces.intern.apitimecloud.dto.TimeDTO;
import com.ces.intern.apitimecloud.entity.EmbedEntity;
import com.ces.intern.apitimecloud.entity.TaskEntity;
import com.ces.intern.apitimecloud.entity.TimeEntity;
import com.ces.intern.apitimecloud.entity.UserEntity;
import com.ces.intern.apitimecloud.http.exception.NotFoundException;
import com.ces.intern.apitimecloud.http.request.TimeRequest;
import com.ces.intern.apitimecloud.http.response.TimeResponse;
import com.ces.intern.apitimecloud.repository.TaskRepository;
import com.ces.intern.apitimecloud.repository.TimeRepository;
import com.ces.intern.apitimecloud.repository.UserRepository;
import com.ces.intern.apitimecloud.service.TimeService;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeServiceImpl implements TimeService {


    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TimeRepository timeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TimeServiceImpl( UserRepository userRepository,
                            TaskRepository taskRepository,
                            TimeRepository timeRepository,
                            ModelMapper modelMapper) {
        this.timeRepository = timeRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public TimeResponse save(String userId, TimeRequest timeRequest, Integer taskId) {
        TypeMap<TimeRequest, TimeEntity> tm = modelMapper.typeMap(TimeRequest.class, TimeEntity.class);
        Converter<Long,Date> converter = (context) -> context.getSource() == null ? null : new Date(context.getSource());
        tm.addMappings(mapping ->{
            mapping.using(converter).map(TimeRequest::getMileSecondEndTime, TimeEntity::setEndTime);
            mapping.using(converter).map(TimeRequest::getMileSecondStartTime, TimeEntity::setStartTime);
        });

        TimeEntity timeEntity = tm.map(timeRequest);
        TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(() ->
                new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage())) ;

        UserEntity userEntity = userRepository.findById(Integer.parseInt(userId))
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()));

        timeEntity.setUser(userEntity);
        timeEntity.setTask(taskEntity);
        EmbedEntity embedEntity = EmbedEntity
                .builder()
                .createAt(new Date())
                .createBy(Integer.parseInt(userId))
                .modifyAt(new Date())
                .modifyBy(Integer.parseInt(userId))
                .build();
        timeEntity.setEmbedEntity(embedEntity);
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
    public void delete(int[] ids) {
        for(int item: ids) {
            TimeEntity timeEntity = timeRepository.findById(item)
                    .orElseThrow(() -> new RuntimeException((ExceptionMessage.NOT_FOUND_RECORD.getMessage() + " with " + item)));
            timeRepository.delete(timeEntity);
        }
    }

    @Override
    public List<TimeDTO> getTimesByUserId(Integer userId) {
        List<TimeEntity> times = timeRepository.getAllByUserId(userId);
        return times.stream()
                .map(time -> modelMapper.map(time, TimeDTO.class))
                .collect(Collectors.toList());
    }
}
