package com.gleb_dev.congratulations_bot.constant.callbackButton;

import lombok.Getter;

/**
 * Buttons of holiday inline menu
 */
@Getter
public enum  HolidayButtonCommand implements CallbackButton {
    BIRTHDAY ("BIRTHDAY", "День Рождения",
            "Поздравляю тебя с Днем Рождения!!! Всего самого наилучшего и долгих лет!"),
    WEDDING("WEDDING","Свадьба",
            "Поздравляю со свадьбой! Счастья молодоженам!!!"),
    SOME_ANNIVERSARY("SOME_ANNIVERSARY", "Какой либо юбилей",
            "Поздравляю с каким-то юбилеем!!! :)"),
    PAYDAY("PAYDAY", "День получки",
            "Поздравляю с лучшим днем в году!!!"),
    HOUSEWARMING("HOUSEWARMING", "Новоселье",
            "Поздравляю с новосельем!!! Надеюсь, ты доволен новым жильем.");

    private String commandName;
    private String text;
    private String congratulation;

    HolidayButtonCommand(String commandName, String text, String congratulation) {
        this.commandName = commandName;
        this.text = text;
        this.congratulation = congratulation;
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
    public String getText() {
        return text;
    }
}
