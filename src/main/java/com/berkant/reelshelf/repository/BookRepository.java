package com.berkant.reelshelf.repository;

import com.berkant.reelshelf.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByGoogleBooksId(String googleBooksId);
}
