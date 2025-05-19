package com.os.os_algo.controller;
import com.os.os_algo.service.LRUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.os.os_algo.service.FIFOService;
import java.util.Map;

@RestController
@RequestMapping("/api/lru")
public class LRUController {

    @Autowired
    private LRUService lruService;

    @PostMapping("/run")
    public Map<String, Object> runLRU(@RequestBody Map<String, Object> payload) {
        int []pages=((java.util.List<Integer>)payload.get("pages")).stream().mapToInt(i->i).toArray();
        int frameSize=(int)payload.get("frameSize");
        System.out.println("LRu Hit");
        return lruService.runLRU(pages,frameSize);
    }

}
