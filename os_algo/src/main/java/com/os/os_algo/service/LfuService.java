package com.os.os_algo.service;

import com.os.os_algo.model.LfuModel;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LfuService {

    public Map<String, Object> simulateLfu(LfuModel input) {
        int[] pages = input.getReferenceArray();
        int n = input.getFrames();

        List<Map<String, Object>> steps = new ArrayList<>();
        Map<Integer, Integer> freqMap = new HashMap<>();
        LinkedHashSet<Integer> frameSet = new LinkedHashSet<>();

        int pageHits = 0;
        int pageFaults = 0;

        for (int i = 0; i < pages.length; i++) {
            int page = pages[i];
            boolean hit = frameSet.contains(page);

            if (hit) {
                freqMap.put(page, freqMap.get(page) + 1);
                pageHits++;
            } else {
                pageFaults++;
                if (frameSet.size() >= n) {
                    int lfu = findLfuPage(freqMap, frameSet);
                    frameSet.remove(lfu);
                    freqMap.remove(lfu);
                }
                frameSet.add(page);
                freqMap.put(page, 1);
            }

            Map<String, Object> step = new HashMap<>();
            step.put("step", i + 1);
            step.put("page", page);
            step.put("frames", new ArrayList<>(frameSet));
            step.put("frequencies", new HashMap<>(freqMap));
            step.put("hit", hit);
            steps.add(step);
        }
        int totalReferences = pages.length;
        pageHits = totalReferences - pageFaults;
        double hitRatio = (double) pageHits / totalReferences * 100;
        double faultRatio = (double) pageFaults / totalReferences * 100;
        Map<String, Object> result = new HashMap<>();
        result.put("steps", steps);
        result.put("pageHits", pageHits);
        result.put("pageFaults", pageFaults);
        result.put("hitRatio", String.format("%.2f", hitRatio));
        result.put("faultRatio", String.format("%.2f", faultRatio));
        return result;
    }

    private int findLfuPage(Map<Integer, Integer> freqMap, Set<Integer> frameSet) {
        int minFreq = Integer.MAX_VALUE;
        int victim = -1;
        for (int page : frameSet) {
            int freq = freqMap.getOrDefault(page, 0);
            if (freq < minFreq) {
                minFreq = freq;
                victim = page;
            }
        }
        return victim;
    }
}
