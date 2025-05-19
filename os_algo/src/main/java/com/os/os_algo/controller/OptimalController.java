package com.os.os_algo.controller;

import com.os.os_algo.service.OptimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/optimal")
public class OptimalController {

    @Autowired
    private OptimalService optimalService;

    @PostMapping("/run")
    public Map<String, Object> runOptimal(@RequestBody Map<String, Object> payload) {
        int[] pages = ((java.util.List<Integer>) payload.get("pages")).stream().mapToInt(i -> i).toArray();
        int frameSize = (int) payload.get("frameSize");
        System.out.println("Optimal endpoint hit!");
        return optimalService.runOptimal(pages, frameSize);
    }
}
