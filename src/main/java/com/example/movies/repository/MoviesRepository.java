package com.example.movies.repository;

import com.example.movies.model.MovieEntity;
import com.example.movies.model.MovieDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface MoviesRepository extends CrudRepository<MovieEntity, String> {
    @Query(value = """
            SELECT M1.PRODUCER AS PRODUCER,\s
                    (M2.MOVIE_YEAR - M1.MOVIE_YEAR) AS YEAR_INTERVAL,
                    M1.MOVIE_YEAR AS PREVIOUS_WIN,
                    M2.MOVIE_YEAR AS FOLLOWING_WIN
            FROM  MOVIES M1 INNER JOIN MOVIES M2 ON M2.PRODUCER = M1.PRODUCER
            WHERE\s
                M1.WIN = TRUE\s
                AND M2.MOVIE_YEAR = (
                SELECT FOLLOWING_MOVIE.MOVIE_YEAR\s
                FROM MOVIES FOLLOWING_MOVIE
                WHERE FOLLOWING_MOVIE.PRODUCER = M1.PRODUCER
                AND FOLLOWING_MOVIE.MOVIE_YEAR > M1.MOVIE_YEAR
                AND FOLLOWING_MOVIE.WIN = TRUE
                ORDER BY FOLLOWING_MOVIE.MOVIE_YEAR ASC
                FETCH FIRST ROW ONLY
            )\s
            ORDER BY YEAR_INTERVAL ASC""", nativeQuery = true)
    List<Tuple> findTopBotProducers();

    List<MovieEntity> findByWin(Boolean win);
}

