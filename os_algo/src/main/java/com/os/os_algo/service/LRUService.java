package com.os.os_algo.service;

import com.os.os_algo.model.LRUModel;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LRUService {

    public Map<String, Object> runLRU(int[] pages, int frameSize) {
        Set<Integer> frame = new HashSet<>();
        List<Integer> usageOrder = new ArrayList<>();
        List<LRUModel> steps = new ArrayList<>();

        int pageFaults = 0;
        int step = 1;

        for (int page : pages) {
            boolean isHit = frame.contains(page);

            if (isHit) {
                usageOrder.remove((Integer) page);  // remove from current position
                usageOrder.add(page);               // add to end (most recent)
            } else {
                pageFaults++;
                if (frame.size() == frameSize) {
                    int lruPage = usageOrder.remove(0); // least recently used
                    frame.remove(lruPage);
                }
                frame.add(page);
                usageOrder.add(page);
            }

            steps.add(new LRUModel(step++, new ArrayList<>(usageOrder), page, isHit));
        }
        int totalReferences = pages.length;
        int pageHits = totalReferences - pageFaults;
        double hitRatio = (double) pageHits / totalReferences * 100;
        double faultRatio = (double) pageFaults / totalReferences * 100;
        Map<String, Object> result = new HashMap<>();
        result.put("steps", steps);
        result.put("totalFaults", pageFaults);
        result.put("totalHits", pages.length - pageFaults);
        result.put("hitRatio", String.format("%.2f", hitRatio));
        result.put("faultRatio", String.format("%.2f", faultRatio));
        return result;
    }
}
