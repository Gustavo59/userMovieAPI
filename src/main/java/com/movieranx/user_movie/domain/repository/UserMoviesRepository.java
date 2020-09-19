package com.movieranx.user_movie.domain.repository;

import com.movieranx.user_movie.domain.domain.UserMovies;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMoviesRepository extends MongoRepository<UserMovies, String> {

    /*
    Param userId
    Param movieId
    Return the relationship instance for a specific user by his id and a specific movie by his id
     */
    UserMovies findByUserIdAndMovieId(String userId, String movieId);
}
