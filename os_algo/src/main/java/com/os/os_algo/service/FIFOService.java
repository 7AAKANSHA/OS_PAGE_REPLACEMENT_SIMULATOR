package com.os.os_algo.service;

import com.os.os_algo.model.StepResult;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FIFOService {

    public Map<String, Object> runFIFO(int[] pages, int frameSize) {
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> set = new HashSet<>();//for unique ones
        List<StepResult> steps = new ArrayList<>();//for step counting

        int pageFaults = 0;
        int stepCounter = 1;

        for (int page : pages) {//iterating through steps of reference strings entering into frames
            boolean isHit = set.contains(page);

            if (!isHit) {
                pageFaults++;
                if (set.size() == frameSize) {
                    int removed = queue.poll();//if reference string is not a hit then remoev it from current frame
                    set.remove(removed);
                }
                queue.add(page);// addd new reference string into teh frame
                set.add(page);//add it to set for tracking the pages
            }

            steps.add(new StepResult(stepCounter++, new ArrayList<>(queue), page, isHit));
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
        result.put("faultRatio", String.format("%.2f", faultRatio));//getting total page hits via total length minus page faults we get the total hits

        return result;
    }
}
