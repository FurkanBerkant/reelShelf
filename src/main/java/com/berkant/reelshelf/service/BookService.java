package com.berkant.reelshelf.service;


import com.berkant.reelshelf.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public void getBook(){
        bookRepository.findAll();
    }

    public void getBookById(Long id){
        bookRepository.findById(id);
    }

    public void saveBook(){
        bookRepository.save(null);
    }

    public void deleteBookById(Long id){
        bookRepository.deleteById(id);
    }
}
