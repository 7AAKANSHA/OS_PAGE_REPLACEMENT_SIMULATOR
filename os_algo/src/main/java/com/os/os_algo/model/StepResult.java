package com.os.os_algo.model;

import java.util.List;

public class StepResult {
    private int step;
    private List<Integer> frameState;
    private int page;
    private boolean isHit;

    public StepResult(int step, List<Integer> frameState, int page, boolean isHit) {
        this.step = step;
        this.frameState = frameState;
        this.page = page;
        this.isHit = isHit;
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
        return isHit;
    }
}
