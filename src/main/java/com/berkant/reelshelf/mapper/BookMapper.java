package com.berkant.reelshelf.mapper;

import com.berkant.reelshelf.dto.AddBookRequest;
import com.berkant.reelshelf.entity.Book;
import com.berkant.reelshelf.entity.UserBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface BookMapper {
    Book toBook(AddBookRequest request);

    @Mapping(target = "book", ignore = true)
    @Mapping(target = "bookStatus", expression = "java(com.berkant.reelshelf.entity.enums.BookStatus.fromId(request.statusId()))")
    UserBook toUserBook(AddBookRequest request);
}