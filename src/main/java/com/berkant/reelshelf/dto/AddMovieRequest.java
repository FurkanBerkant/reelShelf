package com.berkant.reelshelf.dto;

public record AddMovieRequest(
        String name,
        Integer year,
        String genre,
        String description,
        Integer statusId
) {}