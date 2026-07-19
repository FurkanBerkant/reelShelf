package com.berkant.reelshelf.repository;

import com.berkant.reelshelf.entity.UserMovie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserMovieRepository extends JpaRepository<UserMovie, Long> {

    List<UserMovie> findByUserEmail(String email);

    Optional<UserMovie> findByIdAndUserEmail(Long id, String email);

    boolean existsByUserEmailAndMovieId(String email, Long movieId);

}
