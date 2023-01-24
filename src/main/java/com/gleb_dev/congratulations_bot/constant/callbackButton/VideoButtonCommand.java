package com.gleb_dev.congratulations_bot.constant.callbackButton;

import lombok.Getter;

/**
 * Buttons of video inline menu
 */
@Getter
public enum VideoButtonCommand implements CallbackButton {
    VIDEO_COOKING ( "VIDEO_COOKING","кулинария рецепты", "Кулинария"),
    VIDEO_CARS ("VIDEO_CARS","новые машины| авто обзоры", "Машины"),
    VIDEO_SCIENCE_AND_SPACE ("VIDEO_SCIENCE_AND_SPACE","наука космос", "Космос"),
    VIDEO_HISTORY("VIDEO_HISTORY","интересная история человечества", "История"),
    VIDEO_FILMS("VIDEO_FILMS","фильмы сериалы новинки", "Новые фильмы и сериалы"),
    VIDEO_SPORT("VIDEO_SPORT","спорт", "Спорт");


    private String commandName;
    private String searchQuery;
    private String text;

    VideoButtonCommand(String commandName, String searchQuery, String text) {
        this.commandName = commandName;
        this.searchQuery = searchQuery;
        this.text = text;
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
    public String getText() {
        return text;
    }
}
