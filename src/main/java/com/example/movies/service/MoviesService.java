package com.example.movies.service;

import com.example.movies.model.MovieEntity;
import com.example.movies.model.MovieDTO;
import com.example.movies.model.ProducerIntervalDTO;
import com.example.movies.model.TopBotProducersDTO;
import com.example.movies.repository.MoviesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MoviesService {

    private MoviesRepository repository;

    @Autowired
    public MoviesService(MoviesRepository repository) {
        this.repository = repository;
    }

    public TopBotProducersDTO listTopBotProducers() {
        List<ProducerIntervalDTO> b = this.repository.findTopBotProducers()
                .stream()
                .map(m -> {
                    Map<String, Object> maps = new HashMap<>();
                    m.getElements().forEach(te -> maps.put(te.getAlias(), m.get(te.getAlias())));
                    return new ModelMapper().map(maps, ProducerIntervalDTO.class);
                })
                .toList();

        if (!b.isEmpty()) {
            ProducerIntervalDTO firstValue = b.stream().min(Comparator.comparing(ProducerIntervalDTO::getYearInterval)).orElse(new ProducerIntervalDTO());
            ProducerIntervalDTO lastValue = b.stream().max(Comparator.comparing(ProducerIntervalDTO::getYearInterval)).orElse(new ProducerIntervalDTO());
            List<ProducerIntervalDTO> min = b.stream().filter(movie -> Objects.equals(movie.getYearInterval(), firstValue.getYearInterval())).collect(Collectors.toList());
            List<ProducerIntervalDTO> max = b.stream().filter(movie -> Objects.equals(movie.getYearInterval(), lastValue.getYearInterval())).collect(Collectors.toList());
            return new TopBotProducersDTO(min, max);
        }

        return new TopBotProducersDTO();

    }

    public List<MovieDTO> listWinnerProducers() {
        ModelMapper modelMapper = new ModelMapper();
        return this.repository.findByWin(true).stream().map(m -> modelMapper.map(m, MovieDTO.class)).toList();
    }

    public void save(MovieEntity movieEntity) {
        try {
            this.repository.save(movieEntity);
        } catch (DataIntegrityViolationException e) {
            log.warn("Combination of Producer: '{}', Movie: '{}' and Year '{}' already exists in database!", movieEntity.getProducer(), movieEntity.getTitle(), movieEntity.getMovieYear());
        }
    }
}
