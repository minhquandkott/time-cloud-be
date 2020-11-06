package com.ces.intern.apitimecloud.controller;

import com.ces.intern.apitimecloud.dto.TimeDTO;
import com.ces.intern.apitimecloud.http.request.TimeRequest;
import com.ces.intern.apitimecloud.http.response.TimeResponse;
import com.ces.intern.apitimecloud.service.TimeService;
import com.ces.intern.apitimecloud.util.ResponseMessage;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.Date;

@RestController
@RequestMapping("/times")
//@ApiImplicitParams({@ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")}) use for each method
public class TimeController {


    private final TimeService timeService;
    private final ModelMapper modelMapper;

    public TimeController(ModelMapper modelMapper, TimeService timeService){
        this.timeService = timeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/{id}")
    public TimeResponse getTime(@PathVariable Integer id) throws Exception{
        TimeResponse time = timeService.find(id);
        System.out.println(time.getEndTime().getDay());
        return time;
    }

    @PutMapping(value = "/{timeId}")
    public TimeResponse update(@PathVariable Integer timeId,
                               @RequestBody TimeRequest timeRequest,
                               @RequestHeader(value = "userId")Integer userId){

        return timeService.update(timeRequest, userId, timeId);

    }

    @DeleteMapping(value = "/{timeId}")
    public String deleteTime(@PathVariable Integer timeId) {
        timeService.delete(timeId);
        return ResponseMessage.DELETE_SUCCESS;
    }
}
