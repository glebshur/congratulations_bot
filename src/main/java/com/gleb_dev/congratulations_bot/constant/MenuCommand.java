package com.gleb_dev.congratulations_bot.constant;

import lombok.Getter;

/**
 * Commands for commands menu
 */

@Getter
public enum MenuCommand {
    START("/start", "Запуск бота", "после использования команды " +
            "ты можешь начать взаимодействовать с ботом"),
//    SETTINGS("/settings", "Управление настройками", "с помощью этой команды " +
//            "ты можешь изменять свои настройки (язык и т.д.)"),
    HELP("/help", "Информация о боте", "показывает этот список " +
            "всех команд и описания бота");

    private String command;
    private String shortDescription;
    private String fullDescription;

    MenuCommand(String command, String shortDescription, String fullDescription) {
        this.command = command;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
    }
}
