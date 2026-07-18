package com.berkant.reelshelf;

import com.berkant.reelshelf.dto.AddMovieRequest;
import com.berkant.reelshelf.entity.Movie;
import com.berkant.reelshelf.entity.User;
import com.berkant.reelshelf.entity.UserMovie;
import com.berkant.reelshelf.mapper.MovieMapper;
import com.berkant.reelshelf.repository.MovieRepository;
import com.berkant.reelshelf.repository.UserMovieRepository;
import com.berkant.reelshelf.repository.UserRepository;
import com.berkant.reelshelf.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMovieRepository userMovieRepository;

    @Mock
    private MovieMapper movieMapper;

    @InjectMocks
    private MovieService movieService;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void saveMovie_shouldCreateNewMovie_whenMovieDoesNotExist() {
        String email = "test@test.com";
        when(authentication.getName()).thenReturn(email);

        AddMovieRequest request = new AddMovieRequest("Inception", 2010, "Sci-Fi", "Best movie", 3);
        when(movieRepository.findByName(request.name())).thenReturn(null);
        when(userRepository.findByEmail(email)).thenReturn(java.util.Optional.of(new User()));
        when(movieMapper.toMovie(request)).thenReturn(new Movie());
        when(movieMapper.toUserMovie(request)).thenReturn(new UserMovie());

        movieService.saveMovie(request);
        verify(movieRepository, times(1)).save(any(Movie.class));
        verify(userMovieRepository, times(1)).save(any(UserMovie.class));
    }

    @Test
    void saveMovie_shouldUseExistingMovie_whenMovieAlreadyExists() {
        String email = "test@test.com";
        when(authentication.getName()).thenReturn(email);

        AddMovieRequest request = new AddMovieRequest("Inception", 2010, "Sci-Fi", "Best movie", 3);
        Movie existingMovie = new Movie();
        existingMovie.setId(1L);
        existingMovie.setName("Inception");

        when(movieRepository.findByName(request.name())).thenReturn(existingMovie);
        when(userRepository.findByEmail(email)).thenReturn(java.util.Optional.of(new User()));
        when(movieMapper.toUserMovie(request)).thenReturn(new UserMovie());

        movieService.saveMovie(request);

        verify(movieRepository, never()).save(any(Movie.class));
        verify(userMovieRepository, times(1)).save(any(UserMovie.class));
    }

    @Test
    void deleteMovieById_shouldThrowException_whenMovieNotInUserList() {
        String email = "test@test.com";
        when(authentication.getName()).thenReturn(email);
        Long movieId = 999L;

        when(userMovieRepository.findByUserEmailAndMovieId(email, movieId)).thenReturn(null);
        assertThrows(RuntimeException.class, () -> movieService.deleteMovieById(movieId));
        verify(userMovieRepository, never()).delete(any(UserMovie.class));
    }

    @Test
    void deleteMovieById_shouldDelete_whenMovieExistsInUserList() {
        String email = "test@test.com";
        when(authentication.getName()).thenReturn(email);
        Long movieId = 1L;
        UserMovie userMovie = new UserMovie();
        when(userMovieRepository.findByUserEmailAndMovieId(email, movieId)).thenReturn(userMovie);

        movieService.deleteMovieById(movieId);

        verify(userMovieRepository, times(1)).delete(userMovie);
    }
}