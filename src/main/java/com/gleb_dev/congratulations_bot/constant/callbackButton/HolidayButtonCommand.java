package com.gleb_dev.congratulations_bot.constant.callbackButton;

import lombok.Getter;

/**
 * Buttons of holiday inline menu
 */
@Getter
public enum  HolidayButtonCommand implements CallbackButton {
    BIRTHDAY ("BIRTHDAY", "holiday.birthday.text",
            "holiday.birthday.congratulation"),
    WEDDING("WEDDING","holiday.wedding.text",
            "holiday.wedding.congratulation"),
    SOME_ANNIVERSARY("SOME_ANNIVERSARY", "holiday.someAnniversary.text",
            "holiday.someAnniversary.congratulation"),
    PAYDAY("PAYDAY", "holiday.payday.text",
            "holiday.payday.congratulation"),
    HOUSEWARMING("HOUSEWARMING", "holiday.housewarming.text",
            "holiday.housewarming.congratulation");

    private String commandName;
    private String textCode;
    private String congratulationCode;

    HolidayButtonCommand(String commandName, String textCode, String congratulationCode) {
        this.commandName = commandName;
        this.textCode = textCode;
        this.congratulationCode = congratulationCode;
    }

    public static HolidayButtonCommand valueOfCommandName(String commandName){
        for (HolidayButtonCommand command : values()) {
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
