package com.example.movies.rest;

import com.example.movies.model.MovieDTO;
import com.example.movies.model.TopBotProducersDTO;
import com.example.movies.service.MoviesService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Rest API for listing movies and producers information", value = "MoviesController")
@RestController
@RequiredArgsConstructor
@RequestMapping("movies")
public class MoviesController {

    private MoviesService moviesService;

    @Autowired
    public MoviesController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @GetMapping(
            path = "producers/top-bot",
            produces = "application/json")
    public TopBotProducersDTO getTopBotProducers() {
        return this.moviesService.listTopBotProducers();
    }

    @GetMapping(
            path = "producers/winners",
            produces = "application/json")
    public List<MovieDTO> getWinnerProducers() {
        return this.moviesService.listWinnerProducers();
    }
}


