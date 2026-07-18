package com.berkant.reelshelf.entity.converter;

import com.berkant.reelshelf.entity.enums.WatchStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class WatchStatusConverter implements AttributeConverter<WatchStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(WatchStatus attribute) {
        return attribute != null ? attribute.getId() : null;
    }

    @Override
    public WatchStatus convertToEntityAttribute(Integer dbData) {
        return dbData != null ? WatchStatus.fromId(dbData) : null;
    }
}
