package com.gleb_dev.congratulations_bot.entity;

import lombok.Getter;

@Getter
public enum Language {
    RUSSIAN("ru"),
    ENGLISH("en");

    private String languageTag;

    Language(String languageTag) {
        this.languageTag = languageTag;
    }
}
