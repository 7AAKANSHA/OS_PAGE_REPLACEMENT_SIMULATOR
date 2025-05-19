package com.os.os_algo.service;

import com.os.os_algo.model.MfuModel;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MfuService {

    public Map<String, Object> simulateMfu(MfuModel input) {
        int[] pages = input.getReferenceArray();
        int n = input.getFrames();

        List<Map<String, Object>> steps = new ArrayList<>();
        Map<Integer, Integer> freqMap = new HashMap<>();
        LinkedHashSet<Integer> frameSet = new LinkedHashSet<>();

        int pageHits = 0;
        int pageFaults = 0;

        for (int i = 0; i < pages.length; i++) {//iterating through frames to check page hit
            int page = pages[i];
            boolean hit = frameSet.contains(page);

            if (hit) {
                freqMap.put(page, freqMap.get(page) + 1);
                pageHits++;
            } else {
                pageFaults++;
                if (frameSet.size() >= n) {
                    int mfu = findMfuPage(freqMap, frameSet);
                    frameSet.remove(mfu);
                    freqMap.remove(mfu);
                }
                frameSet.add(page);
                freqMap.put(page, 1);
            }

            Map<String, Object> step = new HashMap<>();//addings steps into Hashmap
            step.put("step", i + 1);
            step.put("page", page);
            step.put("frames", new ArrayList<>(frameSet));//frames from array list
            step.put("frequencies", new HashMap<>(freqMap));//frequencies of reference strings
            step.put("hit", hit);//putting hit
            steps.add(step);//adding steps into array
        }
        int totalReferences = pages.length;
        pageHits = totalReferences - pageFaults;
        double hitRatio = (double) pageHits / totalReferences * 100;
        double faultRatio = (double) pageFaults / totalReferences * 100;
        Map<String, Object> result = new HashMap<>();//result storing in hashmap
        result.put("steps", steps);//adding steps, page hits &page faults
        result.put("pageHits", pageHits);
        result.put("pageFaults", pageFaults);
        result.put("hitRatio", String.format("%.2f", hitRatio));
        result.put("faultRatio", String.format("%.2f", faultRatio));
        return result;
    }

    private int findMfuPage(Map<Integer, Integer> freqMap, Set<Integer> frameSet) {
        int maxFreq = Integer.MIN_VALUE;
        int victim = -1;
        for (int page : frameSet) {
            int freq = freqMap.getOrDefault(page, 0);
            if (freq > maxFreq) {
                maxFreq = freq;
                victim = page;
            }
        }
        return victim;
    }
}