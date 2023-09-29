package com.example.movies.service;

import com.example.movies.model.MovieEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileService {

    private MoviesService moviesService;

    @Autowired
    public FileService(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @Value("${file}")
    private String filePath;

    @PostConstruct
    public void importFile() {

        try (Reader reader = new FileReader(filePath)) {
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';').withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
            log.info("Number of lines in CSV: {}", csvParser.getRecordNumber());
            for (CSVRecord csvRecord : csvParser) {
                MovieEntity movieEntity = new MovieEntity();
                movieEntity.setMovieYear(Integer.valueOf(csvRecord.get(0)));
                movieEntity.setTitle(csvRecord.get(1));
                movieEntity.setStudio(csvRecord.get(2));
                movieEntity.setWin(csvRecord.get(4).equalsIgnoreCase("yes"));
                this.trimProducersCell(csvRecord.get(3), movieEntity);
            }
            log.debug("File read successfully!");
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found, check name and extension!");
        } catch (NumberFormatException e) {
            String message = String.format("Incorrect data '%s', please check and try again", e.getMessage());
            throw new IllegalArgumentException(message);
        } catch (Exception e) {
            throw new RuntimeException("Error reading file, please check and try again: " + e.getMessage());
        }
    }

    private void trimProducersCell(String producersCell, MovieEntity movieEntity) {
        String[] producers = producersCell.split("(\\u0020,)|(,\\u0020)|[,]|(\\u0020and\\u0020)|\\u0020\\z");
        for (String producer : producers) {
            movieEntity.setProducer(producer);
            this.moviesService.save(movieEntity);
            movieEntity.setId(null);
        }
    }
}
