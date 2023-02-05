package com.gleb_dev.congratulations_bot.service;

import com.gleb_dev.congratulations_bot.constant.ButtonCommand;
import com.gleb_dev.congratulations_bot.constant.callbackButton.CallbackButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Class that creates keyboards for bot
 */

@Component
public class KeyboardProvider {

    private MessageSource messageSource;

    @Autowired
    public KeyboardProvider(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public ReplyKeyboardMarkup getDefaultKeyboard(Locale locale) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add((messageSource.getMessage(ButtonCommand.CHOOSE_HOLIDAY.getCommandCode(),
                null, locale)));
        row.add((messageSource.getMessage(ButtonCommand.GET_WISH.getCommandCode(),
                null, locale)));
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add((messageSource.getMessage(ButtonCommand.GET_JOKE.getCommandCode(),
                null, locale)));
        row.add((messageSource.getMessage(ButtonCommand.GET_VIDEO.getCommandCode(),
                null, locale)));
        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);
        return keyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineKeyboard(CallbackButton[] buttons, Locale locale) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        Arrays.stream(buttons)
                .forEach(button -> keyboard.add(getSingleButtonInRow(messageSource.getMessage(button.getTextCode(),
                        null, locale), button.getCommandName())));
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }


    private List<InlineKeyboardButton> getSingleButtonInRow(String buttonName, String buttonCallBackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonName);
        button.setCallbackData(buttonCallBackData);

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(button);
        return keyboardButtonsRow;
    }
}
