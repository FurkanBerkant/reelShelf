package com.berkant.reelshelf.repository;

import com.berkant.reelshelf.entity.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {
    List<UserBook> findByUserEmail(String email);

    Optional<UserBook> findByIdAndUserEmail(Long id, String email);

    boolean existsByUserEmailAndBookId(String email, Long bookId);
}
