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
        TimeEntity timeEntity = modelMapper.map(timeRequest, TimeEntity.class);
        TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(() ->
                new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage())) ;

        UserEntity userEntity = userRepository.findById(Integer.parseInt(userID))
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()));

        timeEntity.setUser(userEntity);
        timeEntity.setTask(taskEntity);

        TimeEntity time = timeRepository.save(timeEntity);

        TypeMap<TimeEntity, TimeResponse> tm = modelMapper.typeMap(TimeEntity.class, TimeResponse.class);
        tm.addMapping(src -> src.getUser().getId(), TimeResponse::setUserId);
        tm.addMapping(src -> src.getTask().getId(), TimeResponse::setTaskId);

        timeResponse = tm.map(time);
        return timeResponse;
    }



    @Override
    public TimeResponse find(Integer id) {

        TimeEntity timeEntity = timeRepository.findById(id).orElseThrow(() -> new NotFoundException("Khong tim thay"));

        TimeResponse timeResponse = modelMapper.map(timeEntity, TimeResponse.class);
        timeResponse.setTaskId(timeEntity.getTask().getId());
        timeResponse.setUserId(timeEntity.getUser().getId());
        return timeResponse;
    }

    @Override

    public TimeResponse update(Integer userId, TimeRequest timeRequest, Integer id) {

        TimeEntity timeEntity = timeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + " with" + id));

        timeEntity.setEndTime(timeRequest.getEndTime());
        timeEntity.setStartTime(timeRequest.getStartTime());
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
}
