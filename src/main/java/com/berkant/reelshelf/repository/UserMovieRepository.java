package com.berkant.reelshelf.repository;

import com.berkant.reelshelf.entity.UserMovie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserMovieRepository extends JpaRepository<UserMovie, Long> {

    List<UserMovie> findByUserEmail(String email);

    UserMovie findByUserEmailAndMovieId(String email, Long id);
}
