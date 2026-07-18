package com.berkant.reelshelf.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WatchStatus {
    TO_WATCH(1),
    WATCHING(2),
    WATCHED(3),
    DROPPED(4);

    private final int id;

    public static WatchStatus fromId(int id) {
        for (WatchStatus status : values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        throw new IllegalArgumentException("Geçersiz status id: " + id);
    }
}