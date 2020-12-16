package com.ces.intern.apitimecloud.service;

import com.ces.intern.apitimecloud.dto.TimeDTO;
import com.ces.intern.apitimecloud.http.request.TimeRequest;
import com.ces.intern.apitimecloud.http.response.TimeResponse;

import java.text.ParseException;
import java.util.List;


public interface TimeService {
    TimeResponse save(Integer userId, TimeRequest timeRequest,Integer taskId);
    TimeResponse find(Integer id);
    TimeResponse update(TimeRequest timeRequest, Integer modifiedBy, Integer timeId);
    void delete(Integer timeId);
    List<TimeDTO> getTimesByUserId(Integer userId);
    List<TimeDTO> getAllByUserIdAndDate(Integer userId, String date);
    List<TimeDTO> getAllByTaskId(Integer taskId);
    Float sumTimeByUserId(Integer userId);
    Float sumTimeByTaskId(Integer taskId);
    Float sumTimeByProjectId(Integer projectId);
    Float sumTimeByUserTask(Integer userId, Integer taskId);
    void deleteAllTimeByTaskId(Integer taskId);
    Float sumTimeByUserProject(Integer userId, Integer projectId);
    Float sumTimeByUserDescription(Integer userId,Integer projectId, String description);
    Float sumTimeByDayOfUser(Integer userId, String dateStart, String dateEnd);
    Float sumTimeByWeekOfUser(Integer userId, String date) throws ParseException;
    Float sumTimeByDayOfProject(Integer projectId, String dateStart, String dateEnd);
    List<Float> getAllSumTimesByDayOfWeekOfProject (Integer projectId, String date) throws ParseException;
    List<TimeDTO> getAllTimeByUserIdAndPageable (Integer userId, Integer limit, Integer page, String sortBy, String order);
}
