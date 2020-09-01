package com.ces.intern.apitimecloud.service;

import com.ces.intern.apitimecloud.http.request.TimeRequest;
import com.ces.intern.apitimecloud.http.response.TimeResponse;
import org.springframework.stereotype.Service;

import java.sql.Time;

public interface TimeService {
    public TimeResponse save(String userId, TimeRequest timeRequest);
    public TimeResponse find(Integer id);
    public TimeResponse update(TimeRequest timeRequest, Integer id);
    public void delete(Integer id);
}
