package com.ces.intern.apitimecloud.service.impl;

import com.ces.intern.apitimecloud.dto.TaskDTO;
import com.ces.intern.apitimecloud.dto.TimeDTO;
import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.entity.TimeEntity;
import com.ces.intern.apitimecloud.http.request.TimeRequest;
import com.ces.intern.apitimecloud.http.response.TimeResponse;
import com.ces.intern.apitimecloud.repository.TaskRepository;
import com.ces.intern.apitimecloud.repository.TimeRepository;
import com.ces.intern.apitimecloud.repository.UserRepository;
import com.ces.intern.apitimecloud.service.TimeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeServiceImpl implements TimeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TimeRepository timeRepository;

    @Override
    public TimeResponse save(String userID, TimeRequest timeRequest) {
        TimeEntity timeEntity = new TimeEntity();
        ModelMapper modelMapper = new ModelMapper();
        TimeDTO timeDTO = new TimeDTO();
        TimeResponse timeResponse = new TimeResponse();

        timeDTO = modelMapper.map(timeRequest, TimeDTO.class);
        timeDTO.setTask(modelMapper.map(taskRepository.findById(1), TaskDTO.class));
        timeDTO.setUser(modelMapper.map(userRepository.findById(Integer.parseInt(userID)), UserDTO.class));
        timeEntity = modelMapper.map(timeDTO, TimeEntity.class);
        timeRepository.save(timeEntity);
        timeResponse = modelMapper.map(timeRequest, TimeResponse.class);
        return timeResponse;
    }

    @Override
    public TimeResponse find(Integer id) {
        return null;
    }

    @Override
    public TimeResponse update(TimeRequest timeRequest, Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
