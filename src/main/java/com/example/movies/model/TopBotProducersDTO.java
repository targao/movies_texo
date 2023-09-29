package com.example.movies.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopBotProducersDTO {
    List<ProducerIntervalDTO> min;
    List<ProducerIntervalDTO> max;
}
