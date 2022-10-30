package com.driver;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import  org.springframework.web.bind.annotation.*;
import javax.websocket.server.PathParam;

import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    MovieService movieService;

    MovieController() {

    }

    // #1 Add a movie: POST /movies/add-movie
    @PostMapping("/add-movie")
    public ResponseEntity<Movie>  addMovie(@RequestBody Movie movie) {
        movieService.addMovie(movie);
        return new ResponseEntity<>(movie, HttpStatus.CREATED);
    }
    // #2 Add a Director: POST /movies/add-director
    @PostMapping("/add-director")
    public ResponseEntity<Director>  addDirector(@RequestBody Director director) {
        movieService.addDirector(director);
        return new ResponseEntity<>(director, HttpStatus.CREATED);
    }

    // #3 Pair an existing movie and director: PUT /movies/add-movie-director-pair
    @PutMapping("/add-movie-director-pair")
    public ResponseEntity<String> addMovieDirectorPair(@RequestParam String mName, @RequestParam String dirName) {
        movieService.addMovieDirectorPair(mName, dirName);
        return new ResponseEntity<>("Pair Added Successfully", HttpStatus.OK);
    }

    // #4 Get Movie by movie name: GET /movies/get-movie-by-name/{name}
    @GetMapping("/get-movie-by-name/{name}")
    public ResponseEntity<Movie> getMovieByName(@RequestParam("name")String name) {
        Movie movie = movieService.getMovieByName(name);
        if(movie!=null) return new ResponseEntity<>(movie, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


   // #5 Get Director by director name: GET /movies/get-director-by-name/{name}
    @GetMapping("/get-director-by-name/{name}")
    public ResponseEntity<Director> getDirectorByName(@PathVariable String name) {
        Director director = movieService.getDirectorByName(name);

        if(director!=null) return new ResponseEntity<>(director, HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


    // #6 Get List of movies name for a given director name: GET /movies/get-movies-by-director-name/{director}
    @GetMapping("/get-movies-by-director-name/{director}")
    public ResponseEntity<List<String>> getMoviesByDirectorName(@PathVariable String name) {
        List<String> moviesList = movieService.getMoviesByDirectorName(name);
        return new ResponseEntity<>(moviesList, HttpStatus.OK);
    }


    // #7 Get List of all movies added: GET /movies/get-all-movies
    @GetMapping("/get-all-movies")
    public ResponseEntity<List<String>> findAllMovies() {
        return new ResponseEntity<>(movieService.findAllMovies(), HttpStatus.OK);
    }


   // #8 Delete a director and its movies from the records: DELETE /movies/delete-director-by-name
    @DeleteMapping("/delete-director-by-name")
    public ResponseEntity<String> deleteDirectorByName(@RequestParam String name) {
        movieService.deleteDirectorByName(name);
        return new ResponseEntity<>("Director Deleted", HttpStatus.OK);
    }

   // #9 Delete all directors and all movies by them from the records: DELETE /movies/delete-all-directors
   @DeleteMapping("/delete-all-directors")
   public ResponseEntity<String> deleteAllDirectors() {
        movieService.deleteAllDirectors();
        return new ResponseEntity<>("All Directors Deleted", HttpStatus.OK);
   }

}
