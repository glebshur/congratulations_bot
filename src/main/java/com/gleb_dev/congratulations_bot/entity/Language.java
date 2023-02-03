package com.gleb_dev.congratulations_bot.entity;

import lombok.Getter;

@Getter
public enum Language {
    RUSSIAN("ru", "settings.language.ru.text"),
    ENGLISH("en", "settings.language.en.text");

    private String languageTag;
    private String textCode;

    Language(String languageTag, String textCode) {
        this.languageTag = languageTag;
        this.textCode = textCode;
    }

}
