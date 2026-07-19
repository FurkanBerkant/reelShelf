package com.berkant.reelshelf.service;

import com.berkant.reelshelf.client.TmdbMovieClient;
import com.berkant.reelshelf.dto.MovieRequest;
import com.berkant.reelshelf.dto.MovieSearchResponse;
import com.berkant.reelshelf.dto.tmdb.TmdbMovieDetailsResponse;
import com.berkant.reelshelf.dto.UserMovieResponse;
import com.berkant.reelshelf.entity.Movie;
import com.berkant.reelshelf.entity.User;
import com.berkant.reelshelf.entity.UserMovie;
import com.berkant.reelshelf.entity.enums.WatchStatus;
import com.berkant.reelshelf.mapper.MovieMapper;
import com.berkant.reelshelf.repository.MovieRepository;
import com.berkant.reelshelf.repository.UserMovieRepository;
import com.berkant.reelshelf.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final UserMovieRepository userMovieRepository;
    private final MovieMapper movieMapper;
    private final TmdbMovieClient tmdbMovieClient;

    public List<UserMovieResponse> getMovies() {
        String email = getAuthenticatedEmail();

        return userMovieRepository.findByUserEmail(email)
                .stream()
                .map(userMovie -> movieMapper.toMovieResponse(
                        userMovie,
                        tmdbMovieClient.buildPosterUrl(userMovie.getMovie().getPosterPath())
                ))
                .toList();
    }

    public List<MovieSearchResponse> searchMovies(String query) {
        return tmdbMovieClient.searchMovies(query);
    }

    @Transactional
    public void saveMovie(MovieRequest movieRequest) {
        String email = getAuthenticatedEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        TmdbMovieDetailsResponse tmdbMovie = tmdbMovieClient.getMovieDetails(movieRequest.tmdbId());

        if (tmdbMovie == null || tmdbMovie.id() == null) {
            throw new IllegalArgumentException("Film bulunamadı.");
        }

        Movie movie = movieRepository.findByTmdbId(tmdbMovie.id())
                .orElseGet(() -> movieRepository.save(movieMapper.toMovie(tmdbMovie)));

        if (userMovieRepository.existsByUserEmailAndMovieId(email, movie.getId())) {
            throw new IllegalArgumentException("Bu film zaten listenizde var.");
        }

        UserMovie userMovie = new UserMovie();
        userMovie.setUser(user);
        userMovie.setMovie(movie);
        userMovie.setWatchStatus(WatchStatus.fromId(movieRequest.statusId()));

        userMovieRepository.save(userMovie);
    }

    public void deleteMovieById(Long id) {
        String email = getAuthenticatedEmail();

        UserMovie userMovie = userMovieRepository.findByIdAndUserEmail(id, email)
                .orElseThrow(() -> new RuntimeException("Bu film sizin listenizde bulunamadı veya size ait değil."));

        userMovieRepository.delete(userMovie);
    }

    private String getAuthenticatedEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
