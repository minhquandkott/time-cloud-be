package com.ces.intern.apitimecloud.service;

import com.ces.intern.apitimecloud.http.request.TimeRequest;
import com.ces.intern.apitimecloud.http.response.TimeResponse;


public interface TimeService {
    public TimeResponse save(String userId, TimeRequest timeRequest);
    public TimeResponse find(Integer id);
    public TimeResponse update(Integer userId, TimeRequest timeRequest, Integer id);
    public void delete(int[] ids);
}
