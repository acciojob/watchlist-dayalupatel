
package com.driver;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class MovieRepository {
    HashMap<String, Movie> movieData = new HashMap<>(); // to record all movies
    HashMap<String, Director> directorData = new HashMap<>();  // to record all directors
    HashMap<String, List<String>> pairedData = new HashMap<>(); // to record director-movie pair

    void addMovie(Movie movie) {
        movieData.put(movie.getName(), movie);
    }

    void addDirector(Director director) {
        directorData.put(director.getName(), director);
    }

    void addMovieDirectorPair(String movieName, String directorName) {
        List<String> movieList;
        if(pairedData.containsKey(directorName)) {
            movieList = pairedData.get(directorName);
        }else {
            movieList = new ArrayList<>();
        }
        movieList.add(movieName);
        pairedData.put(directorName, movieList);
    }

    Movie getMovieByName(String name) {
        if(movieData.containsKey(name)) {
            return movieData.get(name);
        }
        return null;
    }

    Director getDirectorByName(String name) {
        if(directorData.containsKey(name)) {
            return directorData.get(name);
        }
        return null;
    }

    List<String> getMoviesByDirectorName(String name) {
        if(pairedData.containsKey(name)) {
            return pairedData.get(name);
        }
        return new ArrayList<>();
    }

    List<String> findAllMovies() {
        List<String> moviesList = new ArrayList<>();
        for(String movieName : movieData.keySet()) {
            moviesList.add(movieName);
        }
        return moviesList;
    }

    void deleteDirectorByName(String dirName) {
        // first deleting all the movies of this director
        List<String> moviesList;
        if(pairedData.containsKey(dirName)) {
            moviesList = pairedData.get(dirName);
        }else {
            moviesList = new ArrayList<>();
        }

        for(int i=0;i<moviesList.size();i++) {
            String movieName = moviesList.get(i);
            if(movieData.containsKey(movieName)) {
                movieData.remove(movieName);
            }
        }
        // deleting this the director
        directorData.remove(dirName);

        // deleting record from pair
        pairedData.remove(dirName);

    }

    void deleteAllDirectors() {
        for(String director : directorData.keySet()) {
            deleteDirectorByName(director);
        }
    }

}
