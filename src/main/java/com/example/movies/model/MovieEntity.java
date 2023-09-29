package com.example.movies.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name="movies")
@Table(indexes = {@Index(name = "unique_title_year_producer_index",  columnList="title, movieYear, producer", unique = true),
                                @Index(name = "movie_producer_index", columnList = "movieYear, producer")})
@AllArgsConstructor
@NoArgsConstructor
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer movieYear;
    private String title;
    private String studio;
    private String producer;
    private Boolean win;
}