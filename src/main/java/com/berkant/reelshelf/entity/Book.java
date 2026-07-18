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
    private String author;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    private String genre;
    private Integer numberOfPages;
    private String description;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserBook> userBooks = new HashSet<>();
}
