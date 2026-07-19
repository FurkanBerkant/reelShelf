package com.berkant.reelshelf.entity;

import com.berkant.reelshelf.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book extends BaseEntity {

    @Column(name = "google_books_id", unique = true, nullable = false)
    private String googleBooksId;

    @Column(length = 1000)
    private String title;

    @Column(length = 1000)
    private String authors;

    private Integer publishedYear;

    @Column(length = 2000)
    private String description;

    private Integer pageCount;

    @Column(length = 1000)
    private String genre;

    @Column(length = 2000)
    private String thumbnailUrl;

    private String language;

    private Double averageRating;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserBook> userBooks = new HashSet<>();

}
