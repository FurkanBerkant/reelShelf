package com.berkant.reelshelf.dto;


public record AddBookRequest(
        String name,
        String author,
        String isbn,
        String description,
        String genre,
        Integer numberOfPages,
        Integer statusId
) {}
