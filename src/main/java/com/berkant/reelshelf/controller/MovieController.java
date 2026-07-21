package com.berkant.reelshelf.controller;

import com.berkant.reelshelf.dto.MovieRequest;
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

    @GetMapping("/{id}")
    public ResponseEntity<UserMovieResponse> getMovieDetail(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieDetail(id));
    }

    @PostMapping()
    public ResponseEntity<String> saveMovie(@RequestBody MovieRequest movieRequest) {
        movieService.saveMovie(movieRequest);
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

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateMovie(@PathVariable Long id, @RequestParam Long statusId) {
        movieService.updateMovie(id, statusId);
        return new ResponseEntity<>("Movie Updated", HttpStatus.OK);
    }
}
