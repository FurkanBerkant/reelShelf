package com.berkant.reelshelf.dto;

import com.berkant.reelshelf.entity.enums.WatchStatus;

public record UserMovieResponse(
        Long id,
        String name,
        Integer year,
        WatchStatus watchStatus
) {}