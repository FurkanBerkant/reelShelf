package com.berkant.reelshelf.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TmdbMovieDetailsResponse(
        Long id,
        String title,
        String overview,

        @JsonProperty("poster_path")
        String posterPath,

        @JsonProperty("release_date")
        String releaseDate,

        @JsonProperty("original_language")
        String originalLanguage,

        @JsonProperty("vote_average")
        Double voteAverage
) {
    public Integer releaseYear() {
        if (releaseDate == null || releaseDate.length() < 4) {
            return null;
        }

        return Integer.valueOf(releaseDate.substring(0, 4));
    }
}