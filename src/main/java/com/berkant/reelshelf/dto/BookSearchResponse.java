package com.berkant.reelshelf.dto;


public record BookSearchResponse(
        String googleBooksId,
        String title,
        String authors,
        Integer publishedYear,
        String description,
        String thumbnailUrl,
        String language,
        Double averageRating
) {}
