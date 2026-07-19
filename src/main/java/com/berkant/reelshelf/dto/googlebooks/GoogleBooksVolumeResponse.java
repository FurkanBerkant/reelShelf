package com.berkant.reelshelf.dto.googlebooks;

import java.util.List;

public record GoogleBooksVolumeResponse(
        String id,
        VolumeInfo volumeInfo
) {
    public record VolumeInfo(
            String title,
            List<String> authors,
            String publishedDate,
            String description,
            Integer pageCount,
            List<String> categories,
            Double averageRating,
            ImageLinks imageLinks,
            String language
    ) {}

    public record ImageLinks(
            String smallThumbnail,
            String thumbnail
    ) {}
}