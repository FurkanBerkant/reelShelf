package com.berkant.reelshelf.mapper;

import com.berkant.reelshelf.dto.UserBookResponse;
import com.berkant.reelshelf.dto.googlebooks.GoogleBooksVolumeResponse;
import com.berkant.reelshelf.entity.Book;
import com.berkant.reelshelf.entity.UserBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;


@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "id", target = "googleBooksId")
    @Mapping(source = "volumeInfo.title", target = "title")
    @Mapping(source = "volumeInfo.authors", target = "authors", qualifiedByName = "joinValues")
    @Mapping(source = "volumeInfo.publishedDate", target = "publishedYear", qualifiedByName = "extractYear")
    @Mapping(source = "volumeInfo.description", target = "description")
    @Mapping(source = "volumeInfo.pageCount", target = "pageCount")
    @Mapping(source = "volumeInfo.categories", target = "genre", qualifiedByName = "joinValues")
    @Mapping(source = "volumeInfo.imageLinks.thumbnail", target = "thumbnailUrl", qualifiedByName = "ensureHttps")
    @Mapping(source = "volumeInfo.language", target = "language")
    @Mapping(source = "volumeInfo.averageRating", target = "averageRating")
    Book toBook(GoogleBooksVolumeResponse response);

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.googleBooksId", target = "googleBooksId")
    @Mapping(source = "book.title", target = "title")
    @Mapping(source = "book.authors", target = "authors")
    @Mapping(source = "book.publishedYear", target = "publishedYear")
    @Mapping(source = "book.description", target = "description")
    @Mapping(source = "book.pageCount", target = "pageCount")
    @Mapping(source = "book.genre", target = "genre")
    @Mapping(source = "book.thumbnailUrl", target = "thumbnailUrl")
    @Mapping(source = "book.language", target = "language")
    @Mapping(source = "book.averageRating", target = "averageRating")
    UserBookResponse toBookResponse(UserBook userBook);

    @Named("joinValues")
    static String joinValues(List<String> values) {
        if (values == null || values.isEmpty()) {
            return null;
        }
        return String.join(", ", values);
    }

    @Named("extractYear")
    static Integer extractYear(String publishedDate) {
        if (publishedDate == null || publishedDate.length() < 4) {
            return null;
        }
        try {
            return Integer.parseInt(publishedDate.substring(0, 4));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Named("ensureHttps")
    static String ensureHttps(String url) {
        if (url == null || url.isBlank()) {
            return null;
        }
        return url.startsWith("http://") ? url.replaceFirst("http://", "https://") : url;
    }
}