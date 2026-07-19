package com.berkant.reelshelf.controller;

import com.berkant.reelshelf.dto.BookRequest;
import com.berkant.reelshelf.dto.BookSearchResponse;
import com.berkant.reelshelf.dto.UserBookResponse;
import com.berkant.reelshelf.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/book")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    @GetMapping()
    public ResponseEntity<List<UserBookResponse>> getMyBooks() {
        return ResponseEntity.ok(bookService.getBooks());
    }

    @PostMapping()
    public ResponseEntity<String> saveBook(@RequestBody BookRequest bookRequest) {
        log.info("Gelen İstek: googleBooksId={}, statusId={}", bookRequest.googleBooksId(), bookRequest.statusId());
        bookService.saveBook(bookRequest);
        return new ResponseEntity<>("Book Saved", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return new ResponseEntity<>("Book Deleted", HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookSearchResponse>> searchBooks(@RequestParam String query) {
        return ResponseEntity.ok(bookService.searchBooks(query));
    }
}
