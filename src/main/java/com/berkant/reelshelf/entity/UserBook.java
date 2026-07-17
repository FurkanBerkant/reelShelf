package com.berkant.reelshelf.entity;

import com.berkant.reelshelf.entity.base.BaseEntity;
import com.berkant.reelshelf.entity.enums.BookStatus;
import com.berkant.reelshelf.entity.enums.WatchStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_book")
@Getter
@Setter
public class UserBook extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookStatus bookStatus;

    private Integer rating;

    private Boolean favorite;

    private Integer currentPage;

    private String notes;
}