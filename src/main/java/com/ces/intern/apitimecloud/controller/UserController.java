package com.ces.intern.apitimecloud.controller;

import com.ces.intern.apitimecloud.dto.ProjectDTO;
import com.ces.intern.apitimecloud.dto.TaskDTO;
import com.ces.intern.apitimecloud.dto.TimeDTO;
import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.http.request.UserRequest;
import com.ces.intern.apitimecloud.http.response.ProjectResponse;
import com.ces.intern.apitimecloud.http.response.TaskResponse;
import com.ces.intern.apitimecloud.http.response.TimeResponse;
import com.ces.intern.apitimecloud.http.response.UserResponse;
import com.ces.intern.apitimecloud.repository.TimeRepository;
import com.ces.intern.apitimecloud.service.ProjectService;
import com.ces.intern.apitimecloud.service.TimeService;
import com.ces.intern.apitimecloud.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {


    private final UserService userService;
    private final ProjectService projectService;
    private final ModelMapper modelMapper;
    private final TimeService timeService;

    @Autowired
    public UserController(UserService userService,
                          ProjectService projectService,
                          ModelMapper modelMapper,
                          TimeService timeService){
        this.userService = userService;
        this.projectService = projectService;
        this.modelMapper = modelMapper;
        this.timeService = timeService;
    }

    @PostMapping(value ="")
    public String createUser(@RequestBody UserRequest userRequest)
    {
        return userService.save(userRequest);
    }

    @GetMapping("/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public UserResponse findUser(@PathVariable Integer id)
    {
        return userService.findUser(id);
    }

    @PutMapping("/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public UserResponse updateUser(@RequestBody UserRequest userRequest, @PathVariable Integer id)
    {
        return userService.update(userRequest, id);
    }

    @DeleteMapping(value = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public String deleteUser(@RequestBody int[] ids)
    {
        userService.delete(ids);
        return "Xóa thành công";
    }

    @GetMapping("/{id}/projects")
    public List<ProjectResponse> getProjectsByUserId(@PathVariable("id") Integer userId){
        List<ProjectDTO> projects = projectService.getAllByUserId(userId);
        return projects.stream()
                .map(project  -> modelMapper.map(project, ProjectResponse.class))
                .collect(Collectors.toList());
    }

    @GetMapping("{id}/times")
    public List<TimeResponse> getTimesByUserId(@PathVariable("id") Integer userId){
        List<TimeDTO> times = timeService.getTimesByUserId(userId);

        return times.stream()
                .map(time  -> modelMapper.map(time, TimeResponse.class))
                .collect(Collectors.toList());
    }
    

}
