package com.movieranx.user_movie.domain.repository;

import com.movieranx.user_movie.domain.domain.UserMovies;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMoviesRepository extends MongoRepository<UserMovies, String> {

    /*
    Param userId
    Param movieId
    Return the relationship instance for a specific user by his id and a specific movie by his id
     */
    UserMovies findByUserIdAndMovieId(String userId, String movieId);

    /*
    Para userId
    Return all results that contains this user id
     */
    List<UserMovies> findAllWatchedMoviesByUserId(String userId);
}
