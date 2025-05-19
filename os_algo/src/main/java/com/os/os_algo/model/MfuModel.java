package com.os.os_algo.model;

public class MfuModel {
    private int frames;
    private String referenceString;

    public MfuModel() {}

    public MfuModel(int frames, String referenceString) {
        this.frames = frames;
        this.referenceString = referenceString;
    }

    public int getFrames() {
        return frames;
    }

    public void setFrames(int frames) {
        this.frames = frames;
    }

    public String getReferenceString() {
        return referenceString;
    }

    public void setReferenceString(String referenceString) {
        this.referenceString = referenceString;
    }

    public int[] getReferenceArray() {
        if (referenceString == null || referenceString.trim().isEmpty()) return new int[0];
        String[] parts = referenceString.trim().split("\\s+");
        int[] arr = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            arr[i] = Integer.parseInt(parts[i]);
        }
        return arr;
    }
}
