package com.berkant.reelshelf.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookStatus {
    TO_READ(1),
    READING(2),
    COMPLETED(3),
    DROPPED(4);

    private final int id;

    public static BookStatus fromId(int id) {
        for (BookStatus status : values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        throw new IllegalArgumentException("Geçersiz kitap durum id: " + id);
    }
}