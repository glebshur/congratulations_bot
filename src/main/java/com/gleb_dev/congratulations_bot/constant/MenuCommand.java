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
    REGISTRATION("/registration", "menuCommand.registration.shortDescription",
            "menuCommand.registration.fullDescription", "menuCommand.registration.answer"),
    MY_DATA("/mydata", "menuCommand.myData.shortDescription",
            "menuCommand.myData.fullDescription", "menuCommand.myData.answer"),
    DELETE_DATA("/deletedata","menuCommand.deleteData.shortDescription",
            "menuCommand.deleteData.fullDescription", "menuCommand.deleteData.answer"),
    HELP("/help", "menuCommand.help.shortDescription",
        "menuCommand.help.fullDescription", "menuCommand.help.answer");

    private String command; // must be in lower case, otherwise telegram api will throw an exception
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
