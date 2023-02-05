package com.gleb_dev.congratulations_bot.converter;

import com.gleb_dev.congratulations_bot.entity.Language;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

/**
 * Converter of Language enum for database
 */

@Converter(autoApply = true)
public class LanguageConverter implements AttributeConverter<Language, String> {
    @Override
    public String convertToDatabaseColumn(Language attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getLanguageTag();
    }

    @Override
    public Language convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return Arrays.stream(Language.values())
                .filter(language -> language.getLanguageTag().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("dbData can't be converted to Language"));
    }
}
