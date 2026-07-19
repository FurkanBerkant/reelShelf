package com.berkant.reelshelf.entity.converter;

import com.berkant.reelshelf.entity.enums.ReadStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ReadStatusConverter implements AttributeConverter<ReadStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ReadStatus attribute) {
        return attribute != null ? attribute.getId() : null;
    }

    @Override
    public ReadStatus convertToEntityAttribute(Integer dbData) {
        return dbData != null ? ReadStatus.fromId(dbData) : null;
    }
}