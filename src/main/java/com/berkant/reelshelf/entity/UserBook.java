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

    @Column(name = "book_status")
    private BookStatus bookStatus;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "favorite")
    private Boolean favorite;

    @Column(name = "current_page")
    private Integer currentPage;

    @Column(name = "notes")
    private String notes;
}