package com.gleb_dev.congratulations_bot.constant.callbackButton;

/**
 * Buttons of settings inline menu
 */
public enum  SettingsButtonCommand implements CallbackButton {
    LANGUAGE_RU("LANGUAGE_RU", "settings.language.ru.text"),
    LANGUAGE_EN("LANGUAGE_EN", "settings.language.en.text");

    private String commandName;
    private String textCode;

    SettingsButtonCommand(String commandName, String textCode) {
        this.commandName = commandName;
        this.textCode = textCode;
    }

    public static SettingsButtonCommand valueOfCommandName(String commandName){
        for (SettingsButtonCommand command : values()) {
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
