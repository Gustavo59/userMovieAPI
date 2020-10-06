package com.movieranx.user_movie.domain.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "UserMovies")
public class UserMovies {

    @Id
    private String id;

    @NotNull
    private String userId;

    @NotNull
    private String movieId;

    private Integer rating;

    private Boolean favorite;

    private Boolean saved;

    private Boolean watched;

    private String watchedDate;

    private String ratingUpdateDate;
}
