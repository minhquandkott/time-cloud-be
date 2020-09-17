package com.ces.intern.apitimecloud.service.impl;

import com.ces.intern.apitimecloud.dto.TaskDTO;
import com.ces.intern.apitimecloud.dto.TimeDTO;
import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.entity.TaskEntity;
import com.ces.intern.apitimecloud.entity.TimeEntity;
import com.ces.intern.apitimecloud.entity.UserEntity;
import com.ces.intern.apitimecloud.http.exception.NotFoundException;
import com.ces.intern.apitimecloud.http.request.TimeRequest;
import com.ces.intern.apitimecloud.http.response.TimeResponse;
import com.ces.intern.apitimecloud.repository.TaskRepository;
import com.ces.intern.apitimecloud.repository.TimeRepository;
import com.ces.intern.apitimecloud.repository.UserRepository;
import com.ces.intern.apitimecloud.service.TaskService;
import com.ces.intern.apitimecloud.service.TimeService;
import com.ces.intern.apitimecloud.service.UserService;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import org.modelmapper.*;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public TimeResponse save(String userID, TimeRequest timeRequest, Integer taskId) {
        TimeResponse timeResponse = new TimeResponse();
        TypeMap<TimeRequest, TimeEntity> tm = modelMapper.typeMap(TimeRequest.class, TimeEntity.class);
        Converter<Long,Date> converter = context -> context.getSource() == null ? null : new Date(context.getSource());
        tm.addMappings(mapping ->{
            mapping.using(converter).map(TimeRequest::getMileSecondEndTime, TimeEntity::setEndTime);
            mapping.using(converter).map(TimeRequest::getMileSecondStartTime, TimeEntity::setStartTime);
        });


        TimeEntity timeEntity = tm.map(timeRequest);
        TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(() ->
                new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage())) ;

        UserEntity userEntity = userRepository.findById(Integer.parseInt(userID))
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()));

        timeEntity.setUser(userEntity);
        timeEntity.setTask(taskEntity);
        TimeEntity time = timeRepository.save(timeEntity);
        timeResponse = modelMapper.map(time, TimeResponse.class);
        System.out.println(modelMapper.getTypeMap(TimeEntity.class, TimeResponse.class).getMappings());


        return timeResponse;
    }



    @Override
    public TimeResponse find(Integer id) {

        TimeEntity timeEntity = timeRepository.findById(id).orElseThrow(() -> new NotFoundException("Khong tim thay"));
        TimeResponse timeResponse = new TimeResponse();
        try {
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
             timeResponse= modelMapper.map(timeEntity, TimeResponse.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        return timeResponse;
    }

    @Override

    public TimeResponse update(Integer userId, TimeRequest timeRequest, Integer id) {

        TimeEntity timeEntity = timeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + " with" + id));

//        timeEntity.setEndTime(timeRequest.getEndTime());
//        timeEntity.setStartTime(timeRequest.getStartTime());
        timeEntity.setDescription( timeRequest.getDescription());

        timeEntity.setTask(timeEntity.getTask());
        timeEntity.setUser(timeEntity.getUser());

        timeEntity = timeRepository.save(timeEntity);

        TimeResponse timeResponse = modelMapper.map(timeEntity, TimeResponse.class);

        return timeResponse;

    }

    @Override
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
