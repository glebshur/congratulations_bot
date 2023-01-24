package com.gleb_dev.congratulations_bot.constant;

import lombok.Getter;
/**
 * Buttons of main keyboard
 */
@Getter
public enum ButtonCommand {
    GET_JOKE("Дай анекдот"),
    GET_VIDEO("Покажи видео"),
    GET_WISH("Хочу пожеланий"),
    CHOOSE_HOLIDAY("Выбрать праздник");

    private String command;

    ButtonCommand(String command) {
        this.command = command;
    }
}
