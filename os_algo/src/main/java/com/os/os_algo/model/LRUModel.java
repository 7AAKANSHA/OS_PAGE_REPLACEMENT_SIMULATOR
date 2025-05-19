package com.os.os_algo.model;

import java.util.List;

public class LRUModel {
    private int step;
    private List<Integer>frameState;
    private int page;
    private boolean hit;


    public LRUModel(int step, List<Integer> frameState, int page, boolean hit) {
        this.step = step;
        this.frameState = frameState;
        this.page = page;
        this.hit = hit;

    }
    public int getStep() {
        return step;
    }

    public List<Integer> getFrameState() {
        return frameState;
    }
    public int getPage() {
        return page;
    }
    public boolean isHit() {
        return hit;
    }
}
