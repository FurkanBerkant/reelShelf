package com.berkant.reelshelf.controller;

import com.berkant.reelshelf.dto.AddMovieRequest;
import com.berkant.reelshelf.dto.MovieSearchResponse;
import com.berkant.reelshelf.dto.UserMovieResponse;
import com.berkant.reelshelf.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping()
    public ResponseEntity<List<UserMovieResponse>> getMyMovies() {
        return ResponseEntity.ok(movieService.getMovies());
    }

    @PostMapping()
    public ResponseEntity<String> saveMovie(@RequestBody AddMovieRequest addMovieRequest) {
        movieService.saveMovie(addMovieRequest);
        return new ResponseEntity<>("Movie Saved", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovieById(@PathVariable Long id) {
        movieService.deleteMovieById(id);
        return new ResponseEntity<>("Movie Deleted", HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieSearchResponse>> searchMovies(@RequestParam String query) {
        return ResponseEntity.ok(movieService.searchMovies(query));
    }
}
