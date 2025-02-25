package com.nayaraperch.testeOutsera.piorfilme.service;

import com.nayaraperch.testeOutsera.piorfilme.entity.*;
import com.nayaraperch.testeOutsera.piorfilme.repository.MovieRepository;
import com.nayaraperch.testeOutsera.piorfilme.repository.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProducerService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ProducerRepository producerRepository;
    public List<Movie> getWinningMovies() {
        List<Movie> winningMovies = movieRepository.findByWinnerTrue();
        return winningMovies;
    }

    public PrizeIntervalResponse getWinningProducers() {
        List<Movie> winningMovies = movieRepository.findByWinnerTrue();

        Map<Producer, List<Year>> producerWins = winningMovies.stream()
                .flatMap(movie -> movie.getProducers().stream().map(producer -> new WinnerProducers(producer, movie.getReleaseYear())))
                .collect(Collectors.groupingBy(WinnerProducers::getProducer, Collectors.mapping(WinnerProducers::getYear, Collectors.toList())));

        List<PrizeInterval> maxList = new ArrayList<>();
        List<PrizeInterval> minList = new ArrayList<>();

        producerWins.forEach((producer, years) -> {
            if (years.size() > 1) {
                years.sort(Comparator.naturalOrder());
                for(int i=0; i < years.size()-1; i++) {
                    int interval = years.get(i+1).getValue() - years.get(i).getValue();
                    PrizeInterval prizeInterval = new PrizeInterval(producer.getName(), interval, years.get(i), years.get(i+1));
                    if (maxList.isEmpty()) {
                        maxList.add(prizeInterval);
                    }else {
                        for (PrizeInterval max: maxList) {
                            if(prizeInterval.getInterval() > max.getInterval()){
                                maxList.clear();
                                maxList.add(prizeInterval);
                            }else if(prizeInterval.getInterval() == max.getInterval()){
                                maxList.add(prizeInterval);
                            }
                        }
                    }
                    if(minList.isEmpty()){
                        minList.add(prizeInterval);
                    }else {
                        for (PrizeInterval min: minList) {
                            if(prizeInterval.getInterval() < min.getInterval()){
                                minList.clear();
                                minList.add(prizeInterval);
                            }else if(min.getInterval() == prizeInterval.getInterval()){
                                minList.add(prizeInterval);
                            }
                        }
                    }
                }
            }
        });

        return new PrizeIntervalResponse(minList, maxList);
    }
    
}
