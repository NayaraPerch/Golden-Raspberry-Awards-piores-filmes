package com.nayaraperch.testeOutsera.piorfilme.service;

import com.nayaraperch.testeOutsera.piorfilme.entity.*;
import com.nayaraperch.testeOutsera.piorfilme.repository.MovieRepository;
import com.nayaraperch.testeOutsera.piorfilme.repository.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProducerService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ProducerRepository producerRepository;


    public PrizeIntervalResponse getPrizeInterval() {
        List<Movie> winningMovies = movieRepository.findByWinnerTrue();
        Map<Producer, List<Year>> producerWins = winningMovies.stream()
                .flatMap(movie -> movie.getProducers().stream().map(producer -> new WinnerProducers(producer, movie.getReleaseYear())))
                .collect(Collectors.groupingBy(WinnerProducers::getProducer, Collectors.mapping(WinnerProducers::getYear, Collectors.toList())));

        List<PrizeInterval> maxList = new ArrayList<>();
        List<PrizeInterval> minList = new ArrayList<>();

        for (Map.Entry<Producer, List<Year>> entry : producerWins.entrySet()) {
            Producer producer = entry.getKey();
            List<Year> years = entry.getValue();
            if (years.size() > 1) {
                years.sort(Comparator.naturalOrder());
                Iterator<Year> yearIterator = years.iterator();
                Year previousYear = yearIterator.next();
                //yearIterator para tratar concorrencia nas listas(ao remover um elemento, o iterador não perde a referência)
                while (yearIterator.hasNext()) {
                    Year currentYear = yearIterator.next();
                    int interval = currentYear.getValue() - previousYear.getValue();
                    PrizeInterval producerInterval = new PrizeInterval(producer.getName(), interval, previousYear, currentYear);
                    if (maxList.isEmpty() || interval > maxList.get(0).getInterval()) {
                        maxList.clear();
                        maxList.add(producerInterval);
                    } else if (interval == maxList.get(0).getInterval()) {
                        maxList.add(producerInterval);
                    }
                    if (minList.isEmpty() || interval < minList.get(0).getInterval()) {
                        minList.clear();
                        minList.add(producerInterval);
                    } else if (interval == minList.get(0).getInterval()) {
                        minList.add(producerInterval);
                    }
                    previousYear = currentYear;
                }
            }
        }

        return new PrizeIntervalResponse(minList, maxList);
    }
    
}
