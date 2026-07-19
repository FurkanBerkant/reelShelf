package com.berkant.reelshelf.service;


import com.berkant.reelshelf.dto.AddBookRequest;
import com.berkant.reelshelf.dto.UserBookResponse;
import com.berkant.reelshelf.entity.*;
import com.berkant.reelshelf.mapper.BookMapper;
import com.berkant.reelshelf.repository.BookRepository;
import com.berkant.reelshelf.repository.UserBookRepository;
import com.berkant.reelshelf.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserBookRepository userBookRepository;
    private final BookMapper bookMapper;
    private final UserRepository userRepository;

    public List<UserBookResponse> getBooks() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userBookRepository.findByUserEmail(email)
                .stream()
                .map(bookMapper::toBookResponse)
                .toList();
    }

    @Transactional
    public void saveBook(AddBookRequest addBookRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Book book = bookRepository.findByName(addBookRequest.name());
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (book == null) {
            book = bookMapper.toBook(addBookRequest);
            bookRepository.save(book);
        }

        UserBook userBook = bookMapper.toUserBook(addBookRequest);
        userBook.setUser(user);
        userBook.setBook(book);
        userBookRepository.save(userBook);
    }

    public void deleteBookById(Long id){
        UserBook userBook = userBookRepository.findByUserEmailAndBookId(SecurityContextHolder.getContext().getAuthentication().getName(), id);
        if (userBook == null) {
            throw new RuntimeException("Bu kitap sizin listenizde bulunamadı veya size ait değil.");
        }
        userBookRepository.delete(userBook);
    }
}
