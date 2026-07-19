package com.berkant.reelshelf.dto;

import com.berkant.reelshelf.entity.enums.ReadStatus;

public record UserBookResponse(
        Long id,
        Long bookId,
        String googleBooksId,
        String title,
        String authors,
        Integer publishedYear,
        String description,
        Integer pageCount,
        String genre,
        String thumbnailUrl,
        String language,
        Double averageRating,
        ReadStatus readStatus
) {}
