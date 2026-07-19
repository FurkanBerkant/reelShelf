package com.berkant.reelshelf.dto;

public record BookRequest(
        String googleBooksId,
        Integer statusId
) {}
