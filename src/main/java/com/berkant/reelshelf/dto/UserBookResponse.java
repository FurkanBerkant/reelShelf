package com.berkant.reelshelf.dto;

import com.berkant.reelshelf.entity.enums.BookStatus;

public record UserBookResponse(
        String name,
        String author,
        String description,
        String genre,
        Integer numberOfPages,
        BookStatus bookStatus
) {}
