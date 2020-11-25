package com.ces.intern.apitimecloud.controller;

import com.ces.intern.apitimecloud.dto.*;
import com.ces.intern.apitimecloud.entity.ProjectUserEntity;
import com.ces.intern.apitimecloud.entity.TimeEntity;
import com.ces.intern.apitimecloud.http.exception.BadRequestException;
import com.ces.intern.apitimecloud.http.request.UserRequest;
import com.ces.intern.apitimecloud.http.response.*;
import com.ces.intern.apitimecloud.repository.ProjectUserRepository;
import com.ces.intern.apitimecloud.repository.TimeRepository;
import com.ces.intern.apitimecloud.service.*;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import com.ces.intern.apitimecloud.util.ResponseMessage;
import com.ces.intern.apitimecloud.util.Utils;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.sql.Time;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/users")
//@ApiImplicitParams({@ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")}) use for each method
public class UserController {

    private final UserService userService;
    private final ProjectService projectService;
    private final ModelMapper modelMapper;
    private final TimeService timeService;
    private final TaskService taskService;
    private final DiscussionService discussionService;
    private final InteractService interactService;
    private final ProjectUserRepository projectUserRepository;

    @Autowired
    public UserController(UserService userService,
                          ProjectService projectService,
                          ModelMapper modelMapper,
                          TimeService timeService,
                          TaskService taskService,
                          DiscussionService discussionService,
                          InteractService interactService,
                          ProjectUserRepository projectUserRepository){
        this.userService = userService;
        this.projectService = projectService;
        this.modelMapper = modelMapper;
        this.timeService = timeService;
        this.taskService = taskService;
        this.discussionService = discussionService;
        this.interactService = interactService;
        this.projectUserRepository = projectUserRepository;
    }

    @PostMapping(value ="")
    public String createUser(@RequestBody UserRequest userRequest)
    {
        return userService.save(userRequest);
    }

    @GetMapping("/{id}")

    public UserResponse findUser(@PathVariable Integer id)
    {
        return userService.findUser(id);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@RequestBody UserRequest userRequest, @PathVariable Integer id, @RequestHeader Integer userId)
    {
        return userService.update(userRequest, id, userId);
    }

    @DeleteMapping(value = "/{userId}")

    public String deleteUser(@PathVariable Integer userId)
    {
        userService.delete(userId);
        return ResponseMessage.DELETE_SUCCESS;
    }

    @GetMapping("/{id}/projects")
    public List<ProjectResponse> getProjectsByUserId(@PathVariable("id") Integer userId){
        List<ProjectDTO> projects = projectService.getAllByUserId(userId);
        projects.sort((o1, o2) -> (int)(o1.getCreateAt().getTime() - o2.getCreateAt().getTime()));
        return projects.stream()
                .map(project  -> modelMapper.map(project, ProjectResponse.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/projects/task-count")
    public List<ProjectResponse> getProjectsByUserIdOrderByTaskCount(@PathVariable("id") Integer userId){
        List<ProjectDTO> projects = projectService.getAllByUserIdOOrderByTaskCount(userId);
        return projects.stream()
                .map(project  -> modelMapper.map(project, ProjectResponse.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/projects-available")
    public List<ProjectResponse> getAllProjectsByUserIdAndIsDoing(@PathVariable("id") Integer userId){
        List<ProjectDTO> projects = projectService.getAllByUserIdAndIsDoing(userId);
        return projects.stream().map(project->modelMapper.map(project,ProjectResponse.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/project-user-available")
    public List<ProjectUserDTO> getAllProjectUserByUserId(@PathVariable("id")Integer userId){
        List<ProjectUserEntity> projectUserEntities = projectUserRepository.getAllByIsDoingAndEmbedId_UserId(true, userId);
        return projectUserEntities
                .stream()
                .map(projectUserEntity -> modelMapper.map(projectUserEntity, ProjectUserDTO.class))
                .collect(Collectors.toList());
    }

    public List<ProjectUserResponse> getAllProjectsByUserIdAndNotDone(@PathVariable("id") Integer userId){
        return null;
    }

    @GetMapping("{id}/times")
    public List<TimeResponse> getTimesByUserId(@PathVariable("id") Integer userId,
                                               @RequestParam(required = false) String date,
                                               @RequestParam(required = false) Integer limit,
                                               @RequestParam(required = false) boolean sort_by_create_at){
        if(date != null){
            String regex = "^(\\d{2})-\\d{2}-*(\\d{4})";
            if(!Pattern.matches(regex, date)){
                throw new BadRequestException(ExceptionMessage.FIELD_NOT_CORRECT.getMessage() + "DD-MM-YYYY");
            }else{

                List<TimeDTO> times = timeService.getAllByUserIdAndDate(userId,date);

                return times.stream()
                        .map(time  -> modelMapper.map(time, TimeResponse.class))
                        .collect(Collectors.toList());
            }

        }else{
            List<TimeDTO> times = timeService.getTimesByUserId(userId);

            return times.stream()
                    .map(time  -> modelMapper.map(time, TimeResponse.class))
                    .collect(Collectors.toList());
        }
    }

    @GetMapping("{id}/times/page")
    public List<TimeResponse> getTimesByUserIdAndPageable(@PathVariable("id") Integer userId,
                                               @RequestParam(value = "limit") Integer limit,
                                               @RequestParam(value = "page") Integer page,
                                               @RequestParam(value = "sort_by", required = false) String sortBy,
                                                          @RequestParam(value = "order", defaultValue = "ASC") String order){
        List<TimeDTO> timeDTOS;
        if(sortBy==null){
            timeDTOS = timeService.getAllTimeByUserIdAndPageable(userId, limit, page, "", order);
        }else{
            if(!Utils.containFiledName(TimeDTO.class, sortBy))
                throw  new BadRequestException(ExceptionMessage.FIELD_NOT_CORRECT.getMessage() + " sortBy " +sortBy);
            timeDTOS = timeService.getAllTimeByUserIdAndPageable(userId, limit, page, sortBy, order );
        }
        return timeDTOS.stream()
                .map(timeDTO -> modelMapper.map(timeDTO, TimeResponse.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}/total-times")
    public Float getSumTimeByUserId(@PathVariable("userId") Integer userId){
        if(userId == null) throw new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.getMessage() + "userId");
        return timeService.sumTimeByUserId(userId);
    }

    @GetMapping("{id}/tasks")
    public List<TaskResponse> getAllTasksByUserId(@PathVariable("id") Integer userId){
        List<TaskDTO> tasks = taskService.getAllTaskByUser(userId);
        return tasks.stream().map(task->modelMapper.map(task,TaskResponse.class)).collect(Collectors.toList());
    }

    @GetMapping("/{userId}/description/{description}/total-times")
    public Float getSumTimeByUserDescription(@PathVariable("userId") Integer userId,@PathVariable("description") String description){
        if(userId == null || description == null ) throw new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.getMessage() + "userId" +" or "+"description");
        return timeService.sumTimeByUserDescription(userId,description);
    }

    @GetMapping("/{userId}/date/{date}/total-times")
    public Float getSumTimeByDayOfUser(@PathVariable("userId") Integer userId,@PathVariable("date") String date) throws ParseException {
        if(userId == null || date == null ) throw new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.getMessage() + "userId" +" or "+"date");
        return timeService.sumTimeByDayOfUser(userId,date,Utils.toNumbersOfDay(date,1));
    }

    @GetMapping("/{userId}/week/{date}/total-times")
    public Float getSumTimeByWeekOfUser(@PathVariable("userId") Integer userId,@PathVariable("date") String date) throws ParseException {
        if(userId == null || date == null ) throw new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.getMessage() + "userId" +" or "+"date");
        return timeService.sumTimeByWeekOfUser(userId,date);
    }

    @GetMapping("/{userId}/project-users")
    public List<ProjectUserResponse> getAllByProjectUserId(@PathVariable("userId") Integer userId){
        if(userId == null) throw new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.getMessage() + "userId");
        List<ProjectUserDTO> projectUsers = projectService.getAllByProjectUserId(userId);
        return projectUsers.stream().map(projectUser->modelMapper.map(projectUser,ProjectUserResponse.class)).collect(Collectors.toList());
    }

    @GetMapping("/{userId}/discussions")
    private List<DiscussionDTO> getDiscussion(
            @PathVariable(value = "userId") Integer userId,
            @RequestParam(value = "limit") Integer limit,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "sort_by", required = false) String sortBy
    ){
        return discussionService.getAllByUserIdInProject(userId, limit, page);
    }

    @GetMapping("/{userId}/interacts")
    private List<InteractDTO> getAllInteractByUserId(@PathVariable("userId") Integer userId){
        if(userId == null) throw new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.getMessage() + "userId");
        return interactService.getAllByUserId(userId);
    }

}
