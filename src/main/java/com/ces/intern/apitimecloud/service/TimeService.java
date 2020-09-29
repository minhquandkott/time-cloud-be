package com.ces.intern.apitimecloud.service;

import com.ces.intern.apitimecloud.dto.TimeDTO;
import com.ces.intern.apitimecloud.entity.TimeEntity;
import com.ces.intern.apitimecloud.http.request.TimeRequest;
import com.ces.intern.apitimecloud.http.response.TimeResponse;
import org.omg.CORBA.INTERNAL;

import java.util.List;


public interface TimeService {
    public TimeResponse save(Integer userId, TimeRequest timeRequest,Integer taskId);
    public TimeResponse find(Integer id);
    public void delete(Integer timeId);
    public List<TimeDTO> getTimesByUserId(Integer userId);
    public List<TimeDTO> getAllByTaskId(Integer taskId);

}
