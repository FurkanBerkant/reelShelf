package com.berkant.reelshelf.entity.enums;

public enum ReadStatus {
    TO_READ(1),
    READING(2),
    COMPLETED(3),
    DROPPED(4);

    private final int id;

    ReadStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static ReadStatus fromId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("statusId boş olamaz.");
        }
        for (ReadStatus status : values()) {
            if (status.id == id) {
                return status;
            }
        }
        throw new IllegalArgumentException("Geçersiz statusId: " + id);
    }
}