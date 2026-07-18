package com.berkant.reelshelf.service;

import com.berkant.reelshelf.dto.AddMovieRequest;
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


@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository  movieRepository;
    private final UserRepository userRepository;
    private final UserMovieRepository userMovieRepository;
    private final MovieMapper movieMapper;

    public void getMovie(){
        movieRepository.findAll();
    }

    public void getMovieById(Long id){
        movieRepository.findById(id);
    }

    @Transactional
    public void saveMovie(AddMovieRequest addMovieRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Movie movie = movieMapper.toMovie(addMovieRequest);

        UserMovie userMovie = movieMapper.toUserMovie(addMovieRequest);
        movieRepository.save(movie);
        userMovie.setUser(user);
        userMovie.setMovie(movie);
        userMovieRepository.save(userMovie);

    }

    public void deleteMovieById(Long id){
        movieRepository.deleteById(id);
    }

}
