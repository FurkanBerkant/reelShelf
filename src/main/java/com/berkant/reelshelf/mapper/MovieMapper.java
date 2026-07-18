package com.berkant.reelshelf.mapper;


import com.berkant.reelshelf.dto.AddMovieRequest;
import com.berkant.reelshelf.entity.Movie;
import com.berkant.reelshelf.entity.UserMovie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    Movie toMovie(AddMovieRequest request);

    @Mapping(target = "movie", ignore = true)
    @Mapping(target = "watchStatus", expression = "java(com.berkant.reelshelf.entity.enums.WatchStatus.fromId(request.statusId()))")
    UserMovie toUserMovie(AddMovieRequest request);
}