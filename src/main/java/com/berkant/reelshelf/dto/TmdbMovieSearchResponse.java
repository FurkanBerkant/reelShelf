package com.berkant.reelshelf.dto;

import java.util.List;

public record TmdbMovieSearchResponse(
        List<TmdbMovieDetailsResponse> results
) {}
