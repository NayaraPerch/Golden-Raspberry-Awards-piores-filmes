package com.nayaraperch.testeOutsera.piorfilme;

import com.nayaraperch.testeOutsera.piorfilme.entity.Execution;
import com.nayaraperch.testeOutsera.piorfilme.entity.Movie;
import com.nayaraperch.testeOutsera.piorfilme.entity.Status;
import com.nayaraperch.testeOutsera.piorfilme.repository.MovieRepository;
import com.nayaraperch.testeOutsera.piorfilme.repository.ProducerRepository;
import com.nayaraperch.testeOutsera.piorfilme.service.MovieService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class MovieServiceIntegrationTest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void testInit() {
        movieService.init();
        Map<List<Movie>, Execution> map = movieService.readMoviesFromCSV("/movielist.csv", new Execution());
        List<Movie> movies = movieRepository.findAll();
        assertThat(map.values().stream().findFirst().get().getStatus()).isEqualTo(Status.SUCCESS);
        assertThat(map.values().stream().findFirst().get().getMessage()).isEqualTo("Movies read from CSV file");
    }

}