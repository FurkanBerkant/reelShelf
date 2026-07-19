package com.berkant.reelshelf.dto;

public record MovieRequest(
        Long tmdbId,
        Integer statusId
) {}