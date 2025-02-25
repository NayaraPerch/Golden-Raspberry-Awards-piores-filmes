package com.nayaraperch.testeOutsera.piorfilme;

import com.nayaraperch.testeOutsera.piorfilme.entity.Movie;
import com.nayaraperch.testeOutsera.piorfilme.entity.PrizeIntervalResponse;
import com.nayaraperch.testeOutsera.piorfilme.entity.Producer;
import com.nayaraperch.testeOutsera.piorfilme.repository.MovieRepository;
import com.nayaraperch.testeOutsera.piorfilme.repository.ProducerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Year;
import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProducerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @BeforeEach
    public void setup() {

        movieRepository.deleteAll();
        producerRepository.deleteAll();

        Producer producer1 = new Producer("Producer 1");
        Producer producer2 = new Producer("Producer 2");

        producerRepository.saveAll(Arrays.asList(producer1, producer2));

        Movie movie1 = new Movie();
        movie1.setTitle("Movie 1");
        movie1.setStudios(Collections.singletonList("Studio 1"));
        movie1.setWinner(true);
        movie1.setReleaseYear(Year.of(2008));
        movie1.setProducers(Arrays.asList(producer1));

        Movie movie2 = new Movie();
        movie2.setTitle("Movie 2");
        movie2.setStudios(Collections.singletonList("Studio 2"));
        movie2.setWinner(true);
        movie2.setReleaseYear(Year.of(2009));
        movie2.setProducers(Arrays.asList(producer1));

        Movie movie3 = new Movie();
        movie3.setTitle("Movie 3");
        movie3.setStudios(Collections.singletonList("Studio 3"));
        movie3.setWinner(true);
        movie3.setReleaseYear(Year.of(2018));
        movie3.setProducers(Arrays.asList(producer2));

        Movie movie4 = new Movie();
        movie4.setTitle("Movie 4");
        movie4.setStudios(Collections.singletonList("Studio 4"));
        movie4.setWinner(true);
        movie4.setReleaseYear(Year.of(2020));
        movie4.setProducers(Arrays.asList(producer2));

        movieRepository.saveAll(Arrays.asList(movie1, movie2, movie3, movie4));
    }

    @Test
    public void testGetProducersWithIntervals() throws Exception {
        mockMvc.perform(get("/producers/prizeInterval")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.min[0].producer").value("Producer 1"))
                .andExpect(jsonPath("$.min[0].interval").value(1))
                .andExpect(jsonPath("$.min[0].previousWin").value(2008))
                .andExpect(jsonPath("$.min[0].followingWin").value(2009))
                .andExpect(jsonPath("$.max[0].producer").value("Producer 2"))
                .andExpect(jsonPath("$.max[0].interval").value(2))
                .andExpect(jsonPath("$.max[0].previousWin").value(2018))
                .andExpect(jsonPath("$.max[0].followingWin").value(2020));
    }

}