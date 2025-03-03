package com.nayaraperch.testeOutsera.piorfilme.service;

import com.nayaraperch.testeOutsera.piorfilme.entity.Execution;
import com.nayaraperch.testeOutsera.piorfilme.entity.Movie;
import com.nayaraperch.testeOutsera.piorfilme.entity.Producer;
import com.nayaraperch.testeOutsera.piorfilme.entity.Status;
import com.nayaraperch.testeOutsera.piorfilme.repository.ExecutionRepository;
import com.nayaraperch.testeOutsera.piorfilme.repository.MovieRepository;
import com.nayaraperch.testeOutsera.piorfilme.repository.ProducerRepository;
import jakarta.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private ExecutionRepository executionRepository;

    private static Logger logger = LoggerFactory.getLogger(MovieService.class);

    @Value("${movielist.csv.path}")
    private String filePath;

    @PostConstruct
    public void init() {
        Execution execution = new Execution();
        execution.setExecutionStart(new Date());
        Map<List<Movie>, Execution> readMoviesMap = readMoviesFromCSV(filePath, execution);
        if(!readMoviesMap.keySet().stream().flatMap(List::stream).collect(Collectors.toList()).isEmpty()){
            movieRepository.saveAll(readMoviesMap.keySet().stream().flatMap(List::stream).collect(Collectors.toList()));
            logger.info("Movies Saved!");
        }
        executionRepository.save(readMoviesMap.values().stream().findFirst().get());
        logger.info("End of reading movies from CSV file");
    }
    public Map <List<Movie>, Execution> readMoviesFromCSV(String filePath, Execution execution) {
        Map <List<Movie>, Execution> readMoviesMap = new HashMap<>();
        List<Movie> movies = new ArrayList<>();
        logger.info("Reading movies from CSV file: " + filePath);
        try {

            InputStream inputStream = getClass().getResourceAsStream(filePath);
            if (inputStream == null) {
                logger.error("CSV file not found {}", filePath);
                Execution executionFileNotFoundError = populateExecution(execution, Status.ERROR, "CSV file not found");
                readMoviesMap.put(movies, executionFileNotFoundError);
                return readMoviesMap;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.newFormat(';').withFirstRecordAsHeader().withTrim());

            if(!isValidateHeaders(csvParser.getHeaderNames())){
                Execution executionInvalidCSV = populateExecution(execution, Status.ERROR, "Invalid headers");
                readMoviesMap.put(movies, executionInvalidCSV);
                return readMoviesMap;
            }

            for (CSVRecord csvRecord : csvParser) {
                movies.add(movieMapper(csvRecord));
            }
            Execution executionSuccess = populateExecution(execution, Status.SUCCESS, "Movies read from CSV file");
            readMoviesMap.put(movies, executionSuccess);
        }catch (Exception e) {
            logger.error("Invalid CSV file", e);
            Execution executionInvalidCSV = populateExecution(execution, Status.ERROR, "Invalid CSV file");
            readMoviesMap.put(movies, executionInvalidCSV);
        }
        return readMoviesMap;
    }

    private boolean isValidateHeaders(List<String> headerNames) {
        if (!headerNames.contains("year") || !headerNames.contains("title") || !headerNames.contains("studios") || !headerNames.contains("producers") || !headerNames.contains("winner")) {
            logger.error("Invalid CSV file. Headers must contain: year, title, studios, producers, winner");
            return false;
        }
        return true;
    }

    private Execution populateExecution(Execution execution, Status status, String message) {
        execution.setStatus(status);
        execution.setExecutionEnd(new Date());
        execution.setMessage(message);
        return execution;
    }

    private Movie movieMapper(CSVRecord csvRecord) {
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
        return movie;
    }

}


