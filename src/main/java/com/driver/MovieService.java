package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    MovieRepository movieRepository;

    void addMovie(Movie movie) {
        movieRepository.addMovie(movie);
    }

    void addDirector(Director director) {
        movieRepository.addDirector(director);
    }

    void addMovieDirectorPair(String movieName, String directorName) {
        movieRepository.addMovieDirectorPair(movieName,directorName);
    }

    Movie getMovieByName(String name) {
        return movieRepository.getMovieByName(name);
    }

    Director getDirectorByName(String name) {
        return movieRepository.getDirectorByName(name);
    }

    List<String> getMoviesByDirectorName(String director) {
        return movieRepository.getMoviesByDirectorName(director);
    }

    List<String> findAllMovies() {
        return movieRepository.findAllMovies();
    }

    void deleteDirectorByName(String name)
    {
        movieRepository.deleteDirectorByName(name);
    }

    void deleteAllDirectors() {
        movieRepository.deleteAllDirectors();
    }

}
