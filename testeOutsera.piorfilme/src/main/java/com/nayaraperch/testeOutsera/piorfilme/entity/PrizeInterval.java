package com.nayaraperch.testeOutsera.piorfilme.entity;

import java.time.Year;

public class PrizeInterval {
    private String producer;
    private Integer interval;
    private Year previousWin;
    private Year followingWin;

    public PrizeInterval() {
    }

    public PrizeInterval(String producer, Integer interval, Year previousWin, Year followingWin) {
        this.producer = producer;
        this.interval = interval;
        this.previousWin = previousWin;
        this.followingWin = followingWin;
    }

    public String getProducer() {
        return producer;
    }

    public Integer getInterval() {
        return interval;
    }

    public Year getPreviousWin() {
        return previousWin;
    }

    public Year getFollowingWin() {
        return followingWin;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public void setPreviousWin(Year previousWin) {
        this.previousWin = previousWin;
    }

    public void setFollowingWin(Year followingWin) {
        this.followingWin = followingWin;
    }
}
