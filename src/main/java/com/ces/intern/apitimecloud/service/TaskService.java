package com.ces.intern.apitimecloud.service;

import com.ces.intern.apitimecloud.dto.TaskDTO;
import com.ces.intern.apitimecloud.entity.TaskUserEntity;
import com.ces.intern.apitimecloud.http.response.TimeSumResponse;

import java.util.List;

public interface TaskService {

    TaskDTO createTask(Integer projectId, TaskDTO taskDTO, Integer userId);

    TaskDTO getTask(Integer taskId);

    List<TaskDTO> getAllTaskByProject(Integer projectId);

    TaskDTO updateTask(Integer projectId, TaskDTO taskDTO, Integer userId);

    void deleteTask(Integer taskId);

    void deleteUserOfTask(Integer taskId, Integer userId);

    Float sumTimeByTask (Integer taskId);

    void addUserToTask(Integer userId, Integer taskId);

    List getAllTaskByUser(Integer userId);

    List<TaskDTO> getAllByUserIdAndProjectId(Integer userId, Integer projectId);

    List<TaskDTO> getAllDidDoingByUserIdAndProjectId(Integer userId, Integer projectId);

    void deleteUserOfAllTaskOfProject(Integer projectId, Integer userId);

    void deleteUsersOfAllTaskOfProject(Integer projectId);

    List<TaskUserEntity> getAllTaskUsersByProjectIdAndUserId(Integer projectId, Integer userId);
}
