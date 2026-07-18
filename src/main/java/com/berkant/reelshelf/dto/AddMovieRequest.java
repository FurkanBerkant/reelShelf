package com.berkant.reelshelf.dto;

import com.berkant.reelshelf.entity.enums.WatchStatus;

public record AddMovieRequest(
        String name,
        Integer year,
        String genre,
        String description,
        Integer statusId
) {}