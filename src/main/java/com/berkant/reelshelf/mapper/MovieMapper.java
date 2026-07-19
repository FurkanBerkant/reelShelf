package com.berkant.reelshelf.mapper;


import com.berkant.reelshelf.dto.TmdbMovieDetailsResponse;
import com.berkant.reelshelf.dto.UserMovieResponse;
import com.berkant.reelshelf.entity.Movie;
import com.berkant.reelshelf.entity.UserMovie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    default Movie toMovie(TmdbMovieDetailsResponse response) {
        Movie movie = new Movie();
        movie.setTmdbId(response.id());
        movie.setTitle(response.title());
        movie.setReleaseYear(response.releaseYear());
        movie.setOverview(response.overview());
        movie.setPosterPath(response.posterPath());
        movie.setOriginalLanguage(response.originalLanguage());
        movie.setVoteAverage(response.voteAverage());
        return movie;
    }

    default UserMovieResponse toMovieResponse(UserMovie userMovie, String posterUrl) {
        Movie movie = userMovie.getMovie();

        return new UserMovieResponse(
                userMovie.getId(),
                movie.getId(),
                movie.getTmdbId(),
                movie.getTitle(),
                movie.getReleaseYear(),
                movie.getOverview(),
                posterUrl,
                movie.getOriginalLanguage(),
                movie.getVoteAverage(),
                userMovie.getWatchStatus()
        );
    }
}
