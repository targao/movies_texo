package com.example.movies;

import com.example.movies.model.ProducerIntervalDTO;
import com.example.movies.model.TopBotProducersDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = MoviesApplication.class)
@AutoConfigureMockMvc
@TestPropertySource( locations = "classpath:application-test.yml")
public class MoviesApplicationTest {

    @LocalServerPort
    Integer port = 8080;

    @Autowired
    public MockMvc mvc;

    @Test
    public void persist_get_1top_1bot() throws Exception {

        ProducerIntervalDTO min = new ProducerIntervalDTO("Clayton Townsend", 2, 2016, 2018);
        ProducerIntervalDTO max = new ProducerIntervalDTO("Buzz Feitshans", 9, 1985, 1994);
        TopBotProducersDTO expected = new TopBotProducersDTO(List.of(min), List.of(max));

        String response = mvc.perform(get("/movies/producers/top-bot")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();

        log.info("{}", mapper.readValue(response, TopBotProducersDTO.class));

        Assertions.assertEquals(expected,mapper.readValue(response, TopBotProducersDTO.class));
    }

    @Test
    public void list_movies_by_producer() throws Exception {

        String response = mvc.perform(get("/movies/producers/winners")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        //Here we have 18 entrys because for every movie with more than 1 producer, we split that into more than one entry for comparisons later
        Assertions.assertEquals(mapper.readValue(response, List.class).size(), 18);
    }
}



