package com.nayaraperch.testeOutsera.piorfilme.service;

import com.nayaraperch.testeOutsera.piorfilme.entity.Movie;
import com.nayaraperch.testeOutsera.piorfilme.entity.Producer;
import com.nayaraperch.testeOutsera.piorfilme.repository.MovieRepository;
import com.nayaraperch.testeOutsera.piorfilme.repository.ProducerRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @PostConstruct
    public void init() {
        List<Movie> movies = readMoviesFromCSV("/movielist.csv");
        movieRepository.saveAll(movies);
        System.out.println("Movies Saved!");
    }
    private List<Movie> readMoviesFromCSV(String filePath) {
        List<Movie> movies = new ArrayList<>();
        System.out.println("Reading movies from CSV file: " + filePath);
        try {
            InputStream inputStream = getClass().getResourceAsStream(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.newFormat(';').withFirstRecordAsHeader().withTrim());
            for (CSVRecord csvRecord : csvParser) {
                Movie movie = new Movie();
                movie.setReleaseYear(Year.parse(csvRecord.get("year")));
                movie.setTitle(csvRecord.get("title"));

                // Pegar cada estúdio e adicionar na lista de estúdios
                String[] studioSplit = csvRecord.get("studios").split(",");
                List<String> studios = Arrays.stream(studioSplit).collect(Collectors.toList());
                movie.setStudios(studios);

                // Pegar cada produtor e adicionar na lista de produtores
                String[] producerSplit = csvRecord.get("producers").replace("and", ",").trim().split(",");
                List<Producer> producers = new ArrayList<>();

                for (String producerName : producerSplit) {
                    Producer producer = producerRepository.findByName(producerName.trim());
                    if (producer == null) {
                        producer = new Producer(producerName.trim());
                        producerRepository.save(producer);
                    }
                    producers.add(producer);
                }
                movie.setProducers(producers);

                // Se o campo winner for vazio, setar como false
                if (csvRecord.isSet("winner") && !csvRecord.get("winner").isEmpty()) {
                    movie.setWinner("yes".equals(csvRecord.get("winner")) ? true : false);
                } else {
                    movie.setWinner(false);
                }
                movies.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }
}


