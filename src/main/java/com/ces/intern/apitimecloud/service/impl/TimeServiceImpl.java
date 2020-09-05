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
import com.ces.intern.apitimecloud.service.TimeService;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import org.modelmapper.Condition;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
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
    public TimeServiceImpl(UserRepository userRepository,
                           TaskRepository taskRepository,
                           TimeRepository timeRepository,
                           ModelMapper modelMapper) {
        this.timeRepository = timeRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TimeResponse save(String userID, TimeRequest timeRequest) {
        TimeEntity timeEntity = new TimeEntity();
        TimeDTO timeDTO = new TimeDTO();
        TimeResponse timeResponse = new TimeResponse();
        timeEntity = modelMapper.map(timeRequest, TimeEntity.class);
        TaskEntity taskEntity = taskRepository.findById(1).get();

        UserEntity userEntity = userRepository.findById(Integer.parseInt(userID)).get();

        timeEntity.setUser(userEntity);
        timeEntity.setTask(taskEntity);

        timeRepository.save(timeEntity);

        return null;
    }

    @Override
    public TimeResponse find(Integer id) {

        TimeEntity timeEntity = timeRepository.findById(id).orElseThrow(() -> new NotFoundException("Khong tim thay"));
        TimeDTO timeDTO = modelMapper.map(timeEntity, TimeDTO.class);
        TimeResponse timeResponse = modelMapper.map(timeDTO, TimeResponse.class);
        timeResponse.setTaskId(timeDTO.getTask().getId());
        timeResponse.setUserId(timeDTO.getUser().getId());
        return timeResponse;
    }

    @Override

    public TimeResponse update(Integer userId, TimeRequest timeRequest, Integer id) {

//        UserEntity userEntity = userRepository.findById(userId).get();
//        TaskEntity taskEntity = taskRepository.findById(1).get();

        TimeEntity timeEntity = timeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + " with" + id));

        
        //timeEntity = modelMapper.map(timeRequest, TimeEntity.class);
        timeEntity.setEndTime(timeRequest.getEndTime());
        timeEntity.setStartTime(timeRequest.getStartTime());
        timeEntity.setDescription( timeRequest.getDescription());
        System.out.println("=============");
        timeEntity.setTask(timeEntity.getTask());
        System.out.println("=============");
        timeEntity.setUser(timeEntity.getUser());
        timeEntity = timeRepository.save(timeEntity);
        TimeDTO timeDTO = modelMapper.map(timeEntity, TimeDTO.class);
        TimeResponse timeResponse = modelMapper.map(timeDTO, TimeResponse.class);
        return timeResponse;
        // ong xong hongrồi đúng ko, test hết ok r trừ cái này thế chiều ông push lên nhá chiều tôi deploy, ok có cái này nè ông
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
