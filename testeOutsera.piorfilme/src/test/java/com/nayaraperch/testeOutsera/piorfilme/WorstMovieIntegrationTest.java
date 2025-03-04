package com.nayaraperch.testeOutsera.piorfilme;

import com.nayaraperch.testeOutsera.piorfilme.entity.Execution;
import com.nayaraperch.testeOutsera.piorfilme.entity.Movie;
import com.nayaraperch.testeOutsera.piorfilme.entity.Status;
import com.nayaraperch.testeOutsera.piorfilme.repository.MovieRepository;
import com.nayaraperch.testeOutsera.piorfilme.service.MovieService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class WorstMovieIntegrationTest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testInit() throws Exception {
        movieService.init();
        Map<List<Movie>, Execution> map = movieService.readMoviesFromCSV("/movielist.csv", new Execution());
        List<Movie> movies = movieRepository.findAll();
        assertThat(map.values().stream().findFirst().get().getStatus()).isEqualTo(Status.SUCCESS);
        assertThat(map.values().stream().findFirst().get().getMessage()).isEqualTo("Movies read from CSV file");

    }

    @Test
    public void testGetProducersWithIntervals() throws Exception {
        mockMvc.perform(get("/producers/prizeInterval")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.min").isNotEmpty())
                .andExpect(jsonPath("$.max").isNotEmpty());

    }

}