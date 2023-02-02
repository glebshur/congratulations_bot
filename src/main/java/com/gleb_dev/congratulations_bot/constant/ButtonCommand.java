package com.gleb_dev.congratulations_bot.constant;

import lombok.Getter;
/**
 * Buttons of main keyboard
 */
@Getter
public enum ButtonCommand {
    GET_JOKE("buttonCommand.getJoke.command"),
    GET_VIDEO("buttonCommand.getVideo.command"),
    GET_WISH("buttonCommand.getWish.command"),
    CHOOSE_HOLIDAY("buttonCommand.chooseHoliday.command");

    private String commandCode;

    ButtonCommand(String commandCode) {
        this.commandCode = commandCode;
    }
}
