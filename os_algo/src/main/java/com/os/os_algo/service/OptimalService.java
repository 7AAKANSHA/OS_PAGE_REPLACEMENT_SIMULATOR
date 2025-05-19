package com.os.os_algo.service;

import com.os.os_algo.model.OptimalModel;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OptimalService {

    public Map<String, Object> runOptimal(int[] pages, int frameSize) {
        List<Integer> frames = new ArrayList<>();
        List<OptimalModel> steps = new ArrayList<>();
        int faults = 0;

        for (int i = 0; i < pages.length; i++) {
            int current = pages[i];
            boolean hit = frames.contains(current);

            if (!hit) {
                faults++;
                if (frames.size() == frameSize) {
                    int indexToReplace = findOptimalIndex(frames, pages, i + 1);
                    frames.set(indexToReplace, current);
                } else {
                    frames.add(current);
                }
            }

            steps.add(new OptimalModel(i + 1, new ArrayList<>(frames), current, hit));
        }
        int totalReferences = pages.length;
       int  pageHits = totalReferences - faults;
        double hitRatio = (double) pageHits / totalReferences * 100;
        double faultRatio = (double) faults / totalReferences * 100;
        Map<String, Object> result = new HashMap<>();
        result.put("steps", steps);
        result.put("totalHits", pages.length - faults);
        result.put("totalFaults", faults);
        result.put("hitRatio", String.format("%.2f", hitRatio));
        result.put("faultRatio", String.format("%.2f", faultRatio));
        return result;
    }

    private int findOptimalIndex(List<Integer> frames, int[] pages, int startIndex) {
        Map<Integer, Integer> nextUse = new HashMap<>();
        for (int frame : frames) {
            nextUse.put(frame, Integer.MAX_VALUE); // default: not used again
            for (int j = startIndex; j < pages.length; j++) {
                if (pages[j] == frame) {
                    nextUse.put(frame, j);
                    break;
                }
            }
        }

        int farthest = -1;
        int index = -1;
        for (int i = 0; i < frames.size(); i++) {
            int page = frames.get(i);
            int next = nextUse.get(page);
            if (next > farthest) {
                farthest = next;
                index = i;
            }
        }

        return index;
    }
}
