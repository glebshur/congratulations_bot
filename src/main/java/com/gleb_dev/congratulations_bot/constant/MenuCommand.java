package com.gleb_dev.congratulations_bot.constant;

import lombok.Getter;

/**
 * Commands for commands menu
 */

@Getter
public enum MenuCommand {
    START("/start", "menuCommand.start.shortDescription",
            "menuCommand.start.fullDescription", "menuCommand.start.answer"),
    SETTINGS("/settings", "menuCommand.settings.shortDescription",
            "menuCommand.settings.fullDescription", "menuCommand.settings.answer"),
    HELP("/help", "menuCommand.help.shortDescription",
        "menuCommand.help.fullDescription", "menuCommand.help.answer");

    private String command;
    private String shortDescriptionCode;
    private String fullDescriptionCode;
    private String answerCode;

    MenuCommand(String command, String shortDescriptionCode, String fullDescriptionCode, String answerCode) {
        this.command = command;
        this.shortDescriptionCode = shortDescriptionCode;
        this.fullDescriptionCode = fullDescriptionCode;
        this.answerCode = answerCode;
    }

}
