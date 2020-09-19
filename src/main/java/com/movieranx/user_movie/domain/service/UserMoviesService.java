package com.movieranx.user_movie.domain.service;

import com.mongodb.DuplicateKeyException;
import com.movieranx.user_movie.domain.domain.UserMovies;
import com.movieranx.user_movie.domain.repository.UserMoviesRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class UserMoviesService {

    @Autowired
    UserMoviesRepository repository;

    public UserMovies findUserMovieRate(String userId, String movieId) {
        UserMovies userMovies = null;

        try {
            userMovies = repository.findByUserIdAndMovieId(userId, movieId);
        } catch (Exception e) {
            log.error("Could not find rating!");
            log.error(e.getMessage());
            throw e;
        }

        return userMovies;
    }

    public UserMovies rateMovie(UserMovies userMovies) {
        UserMovies response;

        try {
            userMovies.setWatchedDate(new Date().toString());

            if (userMovies.getFavorite() == null){
                userMovies.setFavorite(false);
            }

            response = repository.save(userMovies);
        } catch (DuplicateKeyException e) {
            log.error("Could not rate movie, movie already rated");
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Could not rate movie");
            log.error(e.getMessage());
            throw e;
        }

        return response;
    }

    public UserMovies updateRating(UserMovies userMovies, String id) {
        Optional<UserMovies> optional = repository.findById(id);

        if (optional.isPresent()) {
            UserMovies db = optional.get();

            db.setId(userMovies.getId());
            db.setRating(userMovies.getRating());
            db.setFavorite(userMovies.getFavorite());
            db.setRatingUpdateDate(new Date().toString());

            repository.save(db);

            return db;
        } else {
            throw new RuntimeException("Could not update rating");
        }
    }

    public void deleteRating(String id){
        try {
            repository.deleteById(id);
        }catch (Exception e){
            log.error("Could not delete rating!");
            log.error(e.getMessage());
            throw e;
        }
    }

}
