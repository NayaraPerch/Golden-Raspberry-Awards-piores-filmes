package com.nayaraperch.testeOutsera.piorfilme.repository;

import com.nayaraperch.testeOutsera.piorfilme.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByWinnerTrue();
}
