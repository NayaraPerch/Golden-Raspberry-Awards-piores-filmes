package com.nayaraperch.testeOutsera.piorfilme.entity;

import java.util.List;

public class PrizeIntervalResponse {
    private List<PrizeInterval> min;
    private List<PrizeInterval> max;

    public PrizeIntervalResponse() {
    }

    public PrizeIntervalResponse(List<PrizeInterval> min, List<PrizeInterval> max) {
        this.min = min;
        this.max = max;
    }

    public List<PrizeInterval> getMin() {
        return min;
    }

    public void setMin(List<PrizeInterval> min) {
        this.min = min;
    }

    public List<PrizeInterval> getMax() {
        return max;
    }

    public void setMax(List<PrizeInterval> max) {
        this.max = max;
    }
}
