package com.os.os_algo.controller;

import com.os.os_algo.service.FIFOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/fifo")
public class FIFOController {

    @Autowired
    private FIFOService fifoService;

    @PostMapping("/run")
    public Map<String, Object> runFIFO(@RequestBody Map<String, Object> payload) {
        int[] pages = ((java.util.List<Integer>) payload.get("pages")).stream().mapToInt(i -> i).toArray();
        int frameSize = (int) payload.get("frameSize");
        System.out.println("FIFO endpoint hit!");
        return fifoService.runFIFO(pages, frameSize);
    }
}
