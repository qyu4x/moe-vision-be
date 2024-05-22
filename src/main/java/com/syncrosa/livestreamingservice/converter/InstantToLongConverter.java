package com.syncrosa.livestreamingservice.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.Instant;

@Converter
public class InstantToLongConverter implements AttributeConverter<Instant, Long> {
    @Override
    public Long convertToDatabaseColumn(Instant instant) {
        return instant.toEpochMilli();
    }

    @Override
    public Instant convertToEntityAttribute(Long aLong) {
        return Instant.ofEpochMilli(aLong);
    }
}
