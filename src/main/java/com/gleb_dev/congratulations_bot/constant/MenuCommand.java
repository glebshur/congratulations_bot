package com.gleb_dev.congratulations_bot.constant;

import lombok.Getter;

/**
 * Commands for commands menu
 */

@Getter
public enum MenuCommand {
    START("/start", "menuCommand.start.shortDescription",
            "menuCommand.start.fullDescription"),
    SETTINGS("/settings", "menuCommand.settings.shortDescription",
            "menuCommand.settings.fullDescription"),
    HELP("/help", "menuCommand.help.shortDescription",
        "menuCommand.help.fullDescription");

    private String command;
    private String shortDescriptionCode;
    private String fullDescriptionCode;

    MenuCommand(String command, String shortDescriptionCode, String fullDescriptionCode) {
        this.command = command;
        this.shortDescriptionCode = shortDescriptionCode;
        this.fullDescriptionCode = fullDescriptionCode;
    }
}
