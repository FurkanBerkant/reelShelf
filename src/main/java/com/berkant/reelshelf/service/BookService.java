package com.berkant.reelshelf.service;


import com.berkant.reelshelf.client.GoogleBooksClient;
import com.berkant.reelshelf.dto.BookRequest;
import com.berkant.reelshelf.dto.BookSearchResponse;
import com.berkant.reelshelf.dto.UserBookResponse;
import com.berkant.reelshelf.dto.googlebooks.GoogleBooksVolumeResponse;
import com.berkant.reelshelf.entity.*;
import com.berkant.reelshelf.entity.enums.ReadStatus;
import com.berkant.reelshelf.exception.ResourceNotFoundException;
import com.berkant.reelshelf.mapper.BookMapper;
import com.berkant.reelshelf.repository.BookRepository;
import com.berkant.reelshelf.repository.UserBookRepository;
import com.berkant.reelshelf.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final UserBookRepository userBookRepository;
    private final BookMapper bookMapper;
    private final GoogleBooksClient googleBooksClient;

    public List<UserBookResponse> getBooks() {
        String email = getAuthenticatedEmail();

        return userBookRepository.findByUserEmail(email)
                .stream()
                .map(bookMapper::toBookResponse)
                .toList();
    }

    public List<BookSearchResponse> searchBooks(String query) {
        return googleBooksClient.searchBooks(query);
    }

    public UserBookResponse getBookDetail(Long id) {
        String email = getAuthenticatedEmail();

        return bookMapper.toBookResponse(findOwnedBook(id, email));
    }

    @Transactional
    public void saveBook(BookRequest bookRequest) {
        String email = getAuthenticatedEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        GoogleBooksVolumeResponse googleBook = googleBooksClient.getBookDetails(bookRequest.googleBooksId());

        if (googleBook == null || googleBook.id() == null) {
            throw new IllegalArgumentException("Kitap bulunamadı.");
        }

        Book book = bookRepository.findByGoogleBooksId(googleBook.id())
                .orElseGet(() -> bookRepository.save(bookMapper.toBook(googleBook)));

        if (userBookRepository.existsByUserEmailAndBookId(email, book.getId())) {
            throw new IllegalArgumentException("Bu kitap zaten listenizde var.");
        }

        UserBook userBook = new UserBook();
        userBook.setUser(user);
        userBook.setBook(book);
        userBook.setReadStatus(ReadStatus.fromId(bookRequest.statusId()));

        userBookRepository.save(userBook);
    }

    @Transactional
    public void deleteBookById(Long id) {
        String email = getAuthenticatedEmail();

        UserBook userBook = findOwnedBook(id, email);

        userBookRepository.delete(userBook);
    }

    private String getAuthenticatedEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Transactional
    public void updateBook(Long id, Long statusId) {
        String email = getAuthenticatedEmail();

        UserBook userBook = findOwnedBook(id, email);

        userBook.setReadStatus(ReadStatus.fromId(statusId.intValue()));
        userBookRepository.save(userBook);
    }

    private UserBook findOwnedBook(Long id, String email) {
        return userBookRepository.findByIdAndUserEmail(id, email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Bu kitap sizin listenizde bulunamadı veya size ait değil."
                ));
    }
}
