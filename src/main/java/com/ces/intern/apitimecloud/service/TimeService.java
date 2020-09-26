package com.ces.intern.apitimecloud.service;

import com.ces.intern.apitimecloud.dto.TimeDTO;
import com.ces.intern.apitimecloud.entity.TimeEntity;
import com.ces.intern.apitimecloud.http.request.TimeRequest;
import com.ces.intern.apitimecloud.http.response.TimeResponse;

import java.util.List;


public interface TimeService {
    public TimeResponse save(Integer userId, TimeRequest timeRequest,Integer taskId);
    public TimeResponse find(Integer id);
    public void delete(int[] ids);
    public List<TimeDTO> getTimesByUserId(Integer userId);
}
