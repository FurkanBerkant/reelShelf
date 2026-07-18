package com.berkant.reelshelf.repository;

import com.berkant.reelshelf.entity.UserMovie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMovieRepository extends JpaRepository<UserMovie, Long> {
}
