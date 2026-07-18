package com.berkant.reelshelf.controller;

import com.berkant.reelshelf.dto.AddMovieRequest;
import com.berkant.reelshelf.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping()
    public ResponseEntity<String> getMovie(){
        movieService.getMovie();
        return ResponseEntity.ok("Movie details");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getMovieById(@PathVariable Long id){
        movieService.getMovieById(id);
        return ResponseEntity.ok("Movie details");
    }

    @PostMapping()
    public ResponseEntity<String> saveMovie(@RequestBody AddMovieRequest addMovieRequest){
        movieService.saveMovie(addMovieRequest);
        return new ResponseEntity<>("Movie Saved", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovieById(@PathVariable Long id){
        movieService.deleteMovieById(id);
        return new ResponseEntity<>("Movie Deleted", HttpStatus.OK);
    }
}
