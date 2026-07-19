package com.berkant.reelshelf.dto.tmdb;

import java.util.List;

public record TmdbMovieSearchResponse(
        List<TmdbMovieDetailsResponse> results
) {}
