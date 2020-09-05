package com.ces.intern.apitimecloud.controller;

import com.ces.intern.apitimecloud.dto.TimeDTO;
import com.ces.intern.apitimecloud.http.exception.BadRequestException;
import com.ces.intern.apitimecloud.http.request.TimeRequest;
import com.ces.intern.apitimecloud.http.response.TimeResponse;
import com.ces.intern.apitimecloud.security.config.SecurityConfig;
import com.ces.intern.apitimecloud.security.config.SecurityContact;
import com.ces.intern.apitimecloud.service.TimeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/time")
public class TimeController {


    private final TimeService timeService;
    private final ModelMapper modelMapper;

    public TimeController(ModelMapper modelMapper, TimeService timeService){
        this.timeService = timeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/{id}")
    public TimeResponse getTime(@PathVariable Integer id) throws Exception{
        return timeService.find(id);
    }

    @PostMapping(value = "/")
    public TimeResponse createTime(@RequestHeader("userId") String userId,
                             @RequestBody TimeRequest timeRequest) {
        if(timeRequest.getDescription() == null) throw new BadRequestException("Missing time description");
        return timeService.save(userId, timeRequest);
    }

    @PutMapping(value = "/{id}")
    public TimeResponse updateTime(@RequestHeader("userId") String userId,
                                   @RequestBody TimeRequest timeRequest,
                                   @PathVariable Integer id) {
        return timeService.update(Integer.parseInt(userId), timeRequest, id);
    }

    @DeleteMapping(value = "/")
    public String deleteTime(@RequestBody int[] ids) {
        timeService.delete(ids);
        return "Xóa thành công";
    }
}
