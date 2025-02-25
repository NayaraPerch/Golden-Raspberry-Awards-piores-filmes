package com.nayaraperch.testeOutsera.piorfilme.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Producer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long producer_id;

    private String name;

    public Producer() {
    }

    public Producer(String name) {
        this.name = name;
    }

    public Long getProducer_id() {
        return producer_id;
    }

    public String getName() {
        return name;
    }
}
