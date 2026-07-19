package com.berkant.reelshelf.dto;

public record MovieSearchResponse(
        Long tmdbId,
        String title,
        Integer releaseYear,
        String overview,
        String posterUrl,
        String originalLanguage,
        Double voteAverage
) {}
