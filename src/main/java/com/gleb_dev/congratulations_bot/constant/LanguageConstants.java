package com.gleb_dev.congratulations_bot.constant;

import com.gleb_dev.congratulations_bot.entity.Language;

/**
 * Class contains internationalization related constants
 */
public class LanguageConstants {

    public static final Language DEFAULT_LANGUAGE = Language.RUSSIAN;

    public static final String GET_VIDEO_ANSWER_CODE = "buttonCommand.getVideo.answer";
    public static final String CHOOSE_HOLIDAY_ANSWER_CODE = "buttonCommand.chooseHoliday.answer";
    public static final String GET_JOKE_ANSWER_CODE = "buttonCommand.getJoke.answer";
    public static final String SETTINGS_ANSWER = "settings.answer";
    public static final String SETTING_KEYBOARD_CHANGED = "setting.keyboardChanged";
    public static final String COMMAND_NOT_FOUND_CODE = "error.commandNotFound";
    public static final String USER_NOT_REGISTERED_CODE = "error.userNotRegistered";
    public static final String USER_ALREADY_REGISTERED = "error.userAlreadyRegistered";
    public static final String JOKE_NOT_FOUND = "error.jokeNotFound";
    public static final String WISH_NOT_FOUND = "error.wishNotFound";
}
