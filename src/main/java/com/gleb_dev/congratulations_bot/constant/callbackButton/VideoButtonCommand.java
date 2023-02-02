package com.gleb_dev.congratulations_bot.constant.callbackButton;

import lombok.Getter;

/**
 * Buttons of video inline menu
 */
@Getter
public enum VideoButtonCommand implements CallbackButton {
    VIDEO_COOKING ( "VIDEO_COOKING","video.cooking.searchQuery", "video.cooking.text"),
    VIDEO_CARS ("VIDEO_CARS","video.cars.searchQuery", "video.cars.text"),
    VIDEO_SCIENCE_AND_SPACE ("VIDEO_SCIENCE_AND_SPACE","video.scienceAndSpace.searchQuery",
            "video.scienceAndSpace.text"),
    VIDEO_HISTORY("VIDEO_HISTORY","video.history.searchQuery", "video.history.text"),
    VIDEO_FILMS("VIDEO_FILMS","video.films.searchQuery", "video.films.text"),
    VIDEO_SPORT("VIDEO_SPORT","video.sport.searchQuery", "video.sport.text");


    private String commandName;
    private String searchQueryCode;
    private String textCode;

    VideoButtonCommand(String commandName, String searchQueryCode, String textCode) {
        this.commandName = commandName;
        this.searchQueryCode = searchQueryCode;
        this.textCode = textCode;
    }

    public static VideoButtonCommand valueOfCommandName(String commandName){
        for (VideoButtonCommand command : values()) {
            if (command.getCommandName().equals(commandName)) {
                return command;
            }
        }
        return null;
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public String getTextCode() {
        return textCode;
    }
}
