package com.berkant.reelshelf.repository;

import com.berkant.reelshelf.entity.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {
    List<UserBook> findByUserEmail(String email);

    UserBook findByUserEmailAndBookId(String name, Long id);
}
