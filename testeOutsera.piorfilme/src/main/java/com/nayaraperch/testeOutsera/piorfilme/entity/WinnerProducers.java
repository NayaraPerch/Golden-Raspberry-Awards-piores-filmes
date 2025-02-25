package com.nayaraperch.testeOutsera.piorfilme.entity;

import jakarta.persistence.*;

import java.time.Year;

public class WinnerProducers {
    private Producer producer;
    private Year year;

    public WinnerProducers(Producer producer, Year year) {
        this.producer = producer;
        this.year = year;
    }

    public Producer getProducer() {
        return producer;
    }

    public Year getYear() {
        return year;
    }

}
