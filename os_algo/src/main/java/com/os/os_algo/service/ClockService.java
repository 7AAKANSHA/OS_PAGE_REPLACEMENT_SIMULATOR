package com.os.os_algo.service;
import com.os.os_algo.model.ClockModel;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClockService {

    public Map<String, Object> simulateClock(ClockModel input) {
        int[] pages = input.getReferenceArray(); // ✅ correct

        int n = input.getFrames();

        int[] frame = new int[n];
        boolean[] useBit = new boolean[n];
        Arrays.fill(frame, -1);

        List<Map<String, Object>> steps = new ArrayList<>();
        int pointer = 0;
        int pageFaults = 0;
        int pageHits = 0;

        for (int i = 0; i < pages.length; i++) {
            int page = pages[i];
            boolean found = false;

            // Check if page already exists
            for (int j = 0; j < n; j++) {
                if (frame[j] == page) {
                    useBit[j] = true;
                    found = true;
                    pageHits++;
                    break;
                }
            }

            if (!found) {
                while (useBit[pointer]) {
                    useBit[pointer] = false;
                    pointer = (pointer + 1) % n;
                }
                frame[pointer] = page;
                useBit[pointer] = true;
                pointer = (pointer + 1) % n;
                pageFaults++;
            }

            // Record step
            Map<String, Object> step = new HashMap<>();
            step.put("step", i + 1);
            step.put("page", page);
            step.put("frames", Arrays.copyOf(frame, n));
            step.put("useBits", Arrays.copyOf(useBit, n));
            step.put("hit", found);
            steps.add(step);
        }
        int totalReferences = pages.length;
        pageHits = totalReferences - pageFaults;
        double hitRatio = (double) pageHits / totalReferences * 100;
        double faultRatio = (double) pageFaults / totalReferences * 100;
        Map<String, Object> result = new HashMap<>();
        result.put("steps", steps);
        result.put("pageFaults", pageFaults);
        result.put("pageHits", pageHits);
        result.put("hitRatio", String.format("%.2f", hitRatio));
        result.put("faultRatio", String.format("%.2f", faultRatio));

        return result;
    }
}
