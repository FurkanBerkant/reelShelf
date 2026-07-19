package com.berkant.reelshelf.dto;

public record AddMovieRequest(
        Long tmdbId,
        Integer statusId
) {}