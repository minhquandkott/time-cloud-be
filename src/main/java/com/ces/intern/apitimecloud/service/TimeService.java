package com.ces.intern.apitimecloud.service;

import com.ces.intern.apitimecloud.dto.TimeDTO;
import com.ces.intern.apitimecloud.http.request.TimeRequest;
import com.ces.intern.apitimecloud.http.response.TimeResponse;

import java.text.ParseException;
import java.util.List;


public interface TimeService {
    TimeResponse save(Integer userId, TimeRequest timeRequest,Integer taskId);
    TimeResponse find(Integer id);
    void delete(Integer timeId);
    List<TimeDTO> getTimesByUserId(Integer userId);
    List<TimeDTO> getAllByTaskId(Integer taskId);
    Float sumTimeByUserId(Integer userId);
    Float sumTimeByTaskId(Integer taskId);
    Float sumTimeByProjectId(Integer projectId);
    Float sumTimeByUserTask(Integer userId, Integer taskId);
    void deleteAllTimeByTaskId(Integer taskId);
    Float sumTimeByUserProject(Integer userId, Integer projectId);
    Float sumTimeByUserDescription(Integer userId, String description);
    Float sumTimeByDayOfUser(Integer userId, String dateStart, String dateEnd);
    Float sumTimeByWeekOfUser(Integer userId, String date) throws ParseException;
}
