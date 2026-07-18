package com.berkant.reelshelf.controller;

import com.berkant.reelshelf.dto.AddBookRequest;
import com.berkant.reelshelf.dto.UserBookResponse;
import com.berkant.reelshelf.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping()
    public ResponseEntity<List<UserBookResponse>> getBooks() {
        return ResponseEntity.ok(bookService.getBooks());
    }

    @PostMapping()
    public ResponseEntity<String> saveBook(@RequestBody AddBookRequest addBookRequest) {
        bookService.saveBook(addBookRequest);
        return new ResponseEntity<>("Book Saved", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return new ResponseEntity<>("Book Deleted", HttpStatus.OK);
    }
}
