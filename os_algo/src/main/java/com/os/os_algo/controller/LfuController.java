package com.os.os_algo.controller;

import com.os.os_algo.model.LfuModel;
import com.os.os_algo.service.LfuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/lfu")
@CrossOrigin
public class LfuController {

    @Autowired
    private LfuService lfuService;

    @PostMapping("/run")
    public Map<String, Object> getLfuResult(@RequestBody LfuModel model) {
        System.out.println("LFU API Hit");
        return lfuService.simulateLfu(model);
    }
}
