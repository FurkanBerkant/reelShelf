package com.berkant.reelshelf.repository;

import com.berkant.reelshelf.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
