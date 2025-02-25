package com.nayaraperch.testeOutsera.piorfilme.entity;

import jakarta.persistence.*;

import java.time.Year;
import java.util.List;

@Entity
public class Movie {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movie_id;
    private Year releaseYear;
    private String title;
    private List<String> studios;
    @ManyToMany
    @JoinTable(
            name = "movie_producer",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "producer_id"))
    private List<Producer> producers;
    private boolean winner;

    public Movie() {
    }

     public Long getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Long movie_id) {
        this.movie_id = movie_id;
    }
    public Year getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Year releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getStudios() {
        return studios;
    }

    public void setStudios(List<String> studios) {
        this.studios = studios;
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public void setProducers(List<Producer> producers) {
        this.producers = producers;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }
}
