package com.ces.intern.apitimecloud.controller;

import com.ces.intern.apitimecloud.http.request.TimeRequest;
import com.ces.intern.apitimecloud.http.response.TimeResponse;
import com.ces.intern.apitimecloud.security.config.SecurityConfig;
import com.ces.intern.apitimecloud.security.config.SecurityContact;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/time")
public class TimeController {

    @GetMapping(value = "/{id}")
    public TimeResponse getTime(@PathVariable Integer id) {
        return null;
    }

    @PostMapping(value = "/")
    public TimeResponse createTime(@RequestHeader(SecurityContact.HEADER_STRING) String userId,
                             @RequestBody TimeRequest timeRequest) {
        return null;
    }

    @PutMapping(value = "/{id}")
    public TimeResponse updateTime(@RequestBody TimeRequest timeRequest, @PathVariable Integer id) {
        return null;
    }

    @DeleteMapping(value = "/{id}")
    public String deleteTime(@RequestBody int[] ids) {
        return "Xóa thành công";
    }
}
