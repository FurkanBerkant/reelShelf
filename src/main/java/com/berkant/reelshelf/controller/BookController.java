package com.berkant.reelshelf.controller;

import com.berkant.reelshelf.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping()
    public ResponseEntity<String> getBook() {
        bookService.getBook();
        return ResponseEntity.ok("Book details");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getBookById(@PathVariable  Long id) {
        bookService.getBookById(id);
        return ResponseEntity.ok("Book details");
    }

    @PostMapping()
    public ResponseEntity<String> saveBook() {
        bookService.saveBook();
        return new ResponseEntity<>("Book Saved", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return new ResponseEntity<>("Book Deleted", HttpStatus.OK);
    }
}
