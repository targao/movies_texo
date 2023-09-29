package com.example.movies.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProducerIntervalDTO {
    String producer;
    Integer yearInterval;
    Integer previousWin;
    Integer followingWin;
}
