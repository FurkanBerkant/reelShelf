package com.berkant.reelshelf.service;

import com.berkant.reelshelf.dto.AddMovieRequest;
import com.berkant.reelshelf.dto.UserMovieResponse;
import com.berkant.reelshelf.entity.Movie;
import com.berkant.reelshelf.entity.User;
import com.berkant.reelshelf.entity.UserMovie;
import com.berkant.reelshelf.mapper.MovieMapper;
import com.berkant.reelshelf.repository.MovieRepository;
import com.berkant.reelshelf.repository.UserMovieRepository;
import com.berkant.reelshelf.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository  movieRepository;
    private final UserRepository userRepository;
    private final UserMovieRepository userMovieRepository;
    private final MovieMapper movieMapper;

    public List<UserMovieResponse> getMovies() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userMovieRepository.findByUserEmail(email)
                .stream()
                .map(movieMapper::toMovieResponse)
                .toList();
    }

    @Transactional
    public void saveMovie(AddMovieRequest addMovieRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Movie movie = movieRepository.findByName(addMovieRequest.name());
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (movie == null) {
            movie = movieMapper.toMovie(addMovieRequest);
            movieRepository.save(movie);
        }

        UserMovie userMovie = movieMapper.toUserMovie(addMovieRequest);
        userMovie.setUser(user);
        userMovie.setMovie(movie);
        userMovieRepository.save(userMovie);

    }

    public void deleteMovieById(Long id){
        UserMovie userMovie = userMovieRepository.findByUserEmailAndMovieId(SecurityContextHolder.getContext().getAuthentication().getName(), id);
        if (userMovie == null) {
            throw new RuntimeException("Bu film sizin listenizde bulunamadı veya size ait değil.");
        }
        userMovieRepository.delete(userMovie);

    }

}
