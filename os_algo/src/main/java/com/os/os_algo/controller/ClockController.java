package com.os.os_algo.controller;

import com.os.os_algo.service.ClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.os.os_algo.model.ClockModel;


import java.util.Map;

@RestController
@RequestMapping("/api/clock")
public class ClockController {

    @Autowired
    private ClockService clockService;

    @PostMapping("/run")
    public Map<String, Object> getClockResult(@RequestBody ClockModel model) {
        System.out.println("Clock Hit");
        System.out.println("frames = " + model.getFrames());
        System.out.println("referenceString = " + model.getReferenceString());
        return clockService.simulateClock(model);
    }
}
