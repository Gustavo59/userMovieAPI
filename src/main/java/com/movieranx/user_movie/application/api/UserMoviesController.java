package com.movieranx.user_movie.application.api;

import com.mongodb.DuplicateKeyException;
import com.movieranx.user_movie.domain.domain.UserMovies;
import com.movieranx.user_movie.domain.service.UserMoviesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/user/movies")
@Slf4j
public class UserMoviesController {

    @Autowired
    private UserMoviesService service;

    @CrossOrigin("*")
    @GetMapping("/findrate/{userId}/{movieId}")
    public ResponseEntity<?> findUserMovieRate(@PathVariable String userId, @PathVariable String movieId) {
        log.info("Finding User rating for Movie with id: " + movieId);

        UserMovies userMovies;

        try {
            userMovies = service.findUserMovieRate(userId, movieId);
        } catch (Exception e) {
            log.error("Error while finding rating for Movie with id: " + movieId, e.getCause());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(userMovies, HttpStatus.OK);
    }

    @CrossOrigin("*")
    @PostMapping("/rate")
    public ResponseEntity<?> rateMovie(@RequestBody @Valid UserMovies body) {
        log.info("Rating movie started ->");

        UserMovies response;

        try {
            response = service.rateMovie(body);
        } catch (DuplicateKeyException e) {
            log.error("Could not rate movie, movie already rated");
            log.error(e.getMessage());
            return new ResponseEntity<>("Could not rate movie", HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            log.error("Could not rate movie");
            log.error(e.getMessage());
            return new ResponseEntity<>("Could note rate movie", HttpStatus.NOT_MODIFIED);
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @CrossOrigin("*")
    @PutMapping("/updaterating/{id}")
    public ResponseEntity<?> updateRating(@PathVariable String id, @RequestBody @Valid UserMovies body) {
        log.info("Updating rating....");

        body.setId(id);

        UserMovies response;

        try {
            response = service.updateRating(body, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>("Could not update rating", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (response == null) {
            return new ResponseEntity<>("Could not find rating", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @CrossOrigin("*")
    @DeleteMapping("/deleterating/{id}")
    public ResponseEntity<?> deleteRating(@PathVariable String id){
        log.info("Deleting rating.....");

        try{
            service.deleteRating(id);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>("Could not delete rating", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Rating deleted with success!", HttpStatus.OK);
    }

    @CrossOrigin("*")
    @GetMapping("/findwatchedmovies/{userId}")
    public ResponseEntity<?> listAllWatchedMovies(@PathVariable String userId){
        List<UserMovies> userMoviesList;

        try {
            userMoviesList = service.listAllWatchedMovies(userId);
        }catch (Exception e){
            return new ResponseEntity<>("Could not retrieve list with movies watched by this user", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(userMoviesList, HttpStatus.OK);
    }

    @CrossOrigin("*")
    @GetMapping("/findsavedmovies/{userId}")
    public ResponseEntity<?> listAllSavedMovies(@PathVariable String userId){
        List<UserMovies> userMoviesList;

        try {
            userMoviesList = service.listAllSavedMovies(userId);
        }catch (Exception e){
            return new ResponseEntity<>("Could not retrieve list with movies saved by this user", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(userMoviesList, HttpStatus.OK);
    }


    @CrossOrigin("*")
    @GetMapping("getCollaborativeRecommendation")
    public ResponseEntity<?> getCollaborativeRecommendation(){

    }

}
