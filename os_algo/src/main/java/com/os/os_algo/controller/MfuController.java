package com.os.os_algo.controller;

import com.os.os_algo.model.MfuModel;
import com.os.os_algo.service.MfuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/mfu")
@CrossOrigin
public class MfuController {

    @Autowired
    private MfuService mfuService;

    @PostMapping("/run")
    public Map<String, Object> getMfuResult(@RequestBody MfuModel model) {
        System.out.println("MFU API Hit");
        return mfuService.simulateMfu(model);
    }
}