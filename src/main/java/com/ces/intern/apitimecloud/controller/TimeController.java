package com.ces.intern.apitimecloud.controller;

import com.ces.intern.apitimecloud.dto.TimeDTO;
import com.ces.intern.apitimecloud.http.exception.BadRequestException;
import com.ces.intern.apitimecloud.http.request.TimeRequest;
import com.ces.intern.apitimecloud.http.response.TimeResponse;
import com.ces.intern.apitimecloud.security.config.SecurityConfig;
import com.ces.intern.apitimecloud.security.config.SecurityContact;
import com.ces.intern.apitimecloud.service.TimeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/times")
public class TimeController {


    private final TimeService timeService;
    private final ModelMapper modelMapper;

    public TimeController(ModelMapper modelMapper, TimeService timeService){
        this.timeService = timeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public TimeResponse getTime(@PathVariable Integer id) throws Exception{
        return timeService.find(id);
    }


    @PutMapping(value = "/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public TimeResponse updateTime(@RequestHeader("userId") String userId,
                                   @RequestBody TimeRequest timeRequest,
                                   @PathVariable Integer id) {
        return timeService.update(Integer.parseInt(userId), timeRequest, id);
    }

    @DeleteMapping(value = "/")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public String deleteTime(@RequestBody int[] ids) {
        timeService.delete(ids);
        return "Xóa thành công";
    }


}
