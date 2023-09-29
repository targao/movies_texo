package com.example.movies.rest;

import com.example.movies.model.MovieDTO;
import com.example.movies.model.TopBotProducersDTO;
import com.example.movies.service.MoviesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Movies Controller", value = "Rest for listing movies and producers information")
@RestController
@RequiredArgsConstructor
@RequestMapping("movies")
public class MoviesController {

    private MoviesService moviesService;

    @Autowired
    public MoviesController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @ApiOperation("Return a object with two lists, one for the fastest 2 prizes won by same producer and another for the slowst 2 prizes won by the same producer, consecutively.")
    @GetMapping(
            path = "producers/top-bot",
            produces = "application/json")
    public TopBotProducersDTO getTopBotProducers() {
        return this.moviesService.listTopBotProducers();
    }

    @ApiOperation("Return a list of all the producers that have won a prize.")
    @GetMapping(
            path = "producers/winners",
            produces = "application/json")
    public List<MovieDTO> getWinnerProducers() {
        return this.moviesService.listWinnerProducers();
    }
}


