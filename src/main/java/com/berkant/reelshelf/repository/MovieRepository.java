package com.berkant.reelshelf.repository;

import com.berkant.reelshelf.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
