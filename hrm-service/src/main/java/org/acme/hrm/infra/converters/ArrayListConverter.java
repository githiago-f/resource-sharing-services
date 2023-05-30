package org.acme.hrm.infra.converters;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.persistence.Converter;
import jakarta.persistence.AttributeConverter;

@Converter(autoApply = true)
public class ArrayListConverter implements AttributeConverter<List<String>, String> {
    String mapper(List<String> attr) {
        return attr.stream().collect(Collectors.joining(", "));
    }

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return Optional.ofNullable(attribute)
            .map(this::mapper)
            .orElse(null);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return Arrays.asList(dbData.split(", "));
    }
    
}
