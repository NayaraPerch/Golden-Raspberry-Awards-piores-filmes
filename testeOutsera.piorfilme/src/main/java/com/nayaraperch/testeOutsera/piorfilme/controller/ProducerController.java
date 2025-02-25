package com.nayaraperch.testeOutsera.piorfilme.controller;

import com.nayaraperch.testeOutsera.piorfilme.entity.PrizeInterval;
import com.nayaraperch.testeOutsera.piorfilme.entity.PrizeIntervalResponse;
import com.nayaraperch.testeOutsera.piorfilme.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/producers")
public class ProducerController {
    @Autowired
    private ProducerService producerService;
    @GetMapping("/winner")
    public PrizeIntervalResponse getWinningProducers() {
        PrizeIntervalResponse  winningMovies = producerService.getWinningProducers();
        return winningMovies;
    }
}
