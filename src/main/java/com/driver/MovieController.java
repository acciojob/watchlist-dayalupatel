package com.driver;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("movies")
public class MovieController {
    List<Movie> dataMovies;
    List<Director> dataDirectors;
    HashMap<String, List<String>> directorMovieData;

    MovieController() {
        this.dataMovies = new ArrayList<>();
        this.dataDirectors = new ArrayList<>();
        this.directorMovieData = new HashMap<>();
    }

    // #1 Add a movie: POST /movies/add-movie
    @PostMapping("/add-movie")
    public ResponseEntity<Movie>  addMovie(@RequestBody Movie movie) {
        dataMovies.add(movie);
        return new ResponseEntity<>(movie, HttpStatus.CREATED);
    }
    // #2 Add a Director: POST /movies/add-director
    @PostMapping("/add-director")
    public ResponseEntity<Director>  addDirector(@RequestBody Director director) {
        dataDirectors.add(director);
        return new ResponseEntity<>(director, HttpStatus.CREATED);
    }

    // #3 Pair an existing movie and director: PUT /movies/add-movie-director-pair
    @PutMapping("/add-movie-director-pair")
    public ResponseEntity<String> addMovieDirectorPair(@RequestParam String mName, @RequestParam String dirName) {
        List<String> list;
        if (directorMovieData.containsKey(dirName)) {
            list = directorMovieData.get(dirName);
        } else {
            list = new ArrayList<>();
        }
        list.add(mName);
        directorMovieData.put(dirName, list);

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    // #4 Get Movie by movie name: GET /movies/get-movie-by-name/{name}
    @GetMapping("/get-movie-by-name/{name}")
    public ResponseEntity<Movie> getMovieByName(@RequestParam("name")String name) {
        for(Movie movie : dataMovies) {
            if(movie.getName().equals(name)) {
                return new ResponseEntity<>(movie, HttpStatus.FOUND);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

   // #5 Get Director by director name: GET /movies/get-director-by-name/{name}
    @GetMapping("/get-director-by-name/{name}")
    public ResponseEntity<Director> getDirectorByName(@RequestParam("name")String name) {
        for(Director director : dataDirectors) {
            if(director.getName().equals(name)) {
                return new ResponseEntity<>(director, HttpStatus.FOUND);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    // #6 Get List of movies name for a given director name: GET /movies/get-movies-by-director-name/{director}
    @GetMapping("/get-movies-by-director-name/{director}")
    public ResponseEntity<List<String>> getMoviesByDirectorName(@PathParam("director")String name) {
        Director director = null;
        for(Director d : dataDirectors) {
            if(d.getName().equals(name)) {
                director = d;
                break;
            }
        }
        if(director==null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        List<String> movieList = directorMovieData.get(dataDirectors);
        return new ResponseEntity<>(movieList, HttpStatus.FOUND);
    }

    // #7 Get List of all movies added: GET /movies/get-all-movies
    @GetMapping("/get-all-movies")
    public ResponseEntity<List<Movie>> findAllMovies() {
        return new ResponseEntity<>(dataMovies, HttpStatus.OK);
    }

   // #8 Delete a director and its movies from the records: DELETE /movies/delete-director-by-name
    @DeleteMapping("/delete-director-by-name")
    public ResponseEntity<Director> deleteDirectorByName(@PathParam("name")String name) {
        // Deleting Director Record;
        for(Director director : dataDirectors) {
            if(director.getName().equals(name)) {
                dataDirectors.remove(director);
            }
        }
        // Deleting its Movies
        List<String> list = directorMovieData.get(name);
        for(int i=0;i<list.size();i++) {
            for(Movie movie : dataMovies) {
                if(movie.getName().equals(list.get(i))) {
                    dataMovies.remove(movie);
                }
            }
        }
        // deleting from map
        directorMovieData.remove(name);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
   // #9 Delete all directors and all movies by them from the records: DELETE /movies/delete-all-directors
   @DeleteMapping("/delete-all-directors")
   public ResponseEntity<String> deleteAllDirectors() {
        for(String dirName : directorMovieData.keySet()) {
            deleteDirectorByName(dirName);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
   }

}
