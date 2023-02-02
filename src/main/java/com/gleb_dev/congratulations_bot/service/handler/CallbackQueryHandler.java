package com.gleb_dev.congratulations_bot.service.handler;

import com.gleb_dev.congratulations_bot.constant.callbackButton.HolidayButtonCommand;
import com.gleb_dev.congratulations_bot.constant.callbackButton.SettingsButtonCommand;
import com.gleb_dev.congratulations_bot.constant.callbackButton.VideoButtonCommand;
import com.gleb_dev.congratulations_bot.service.KeyboardProvider;
import com.gleb_dev.congratulations_bot.service.YouTubeSearchProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.io.Serializable;
import java.util.Collections;
import java.util.Locale;

/**
 * Class that process callback queries
 */

@Component
@Slf4j
public class CallbackQueryHandler {

    private YouTubeSearchProvider youTubeSearchProvider;
    private MessageSource messageSource;
    private KeyboardProvider keyboardProvider;

    @Autowired
    public CallbackQueryHandler(YouTubeSearchProvider youTubeSearchProvider, MessageSource messageSource, KeyboardProvider keyboardProvider) {
        this.youTubeSearchProvider = youTubeSearchProvider;
        this.messageSource = messageSource;
        this.keyboardProvider = keyboardProvider;
    }

    /**
     * Method processes incoming queries
     * @param callbackQuery query that needs to be processed
     * @return response to query received
     */
    public BotApiMethod<Serializable> processCallbackQuery(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        String languageTag = "ru";
        Locale locale = Locale.forLanguageTag(languageTag);

        log.info("User \"{}\" sent callback query with data: {}",
                callbackQuery.getMessage().getChat().getFirstName(),
                data);

        long chatId = callbackQuery.getMessage().getChatId();
        int messageId = callbackQuery.getMessage().getMessageId();

        VideoButtonCommand videoCommand = VideoButtonCommand.valueOfCommandName(data);
        if (videoCommand != null) {
            return handleVideoCommand(videoCommand, chatId, messageId, locale);
        }

        HolidayButtonCommand holidayCommand = HolidayButtonCommand.valueOfCommandName(data);
        if(holidayCommand != null){
            return createEditMessage(messageSource.getMessage(holidayCommand.getCongratulationCode(),
                    null,
                    locale), chatId, messageId);
        }

        SettingsButtonCommand settingsCommand = SettingsButtonCommand.valueOfCommandName(data);
        if(settingsCommand != null){
            return handleSettingsCommand(settingsCommand, chatId, messageId, locale);
        }

        return createEditMessage(messageSource.getMessage("commandNotFound", null, locale),
                chatId, messageId);
    }

    private BotApiMethod<Serializable> handleVideoCommand(VideoButtonCommand command,
                                                          long chatId,
                                                          int messageId,
                                                          Locale locale) {
        String answer = youTubeSearchProvider.getOneRandomVideoLink(messageSource.getMessage(command.getSearchQueryCode(),
                null,
                locale), 10);
        return createEditMessage(answer, chatId, messageId);
    }

    private BotApiMethod<Serializable> handleSettingsCommand(SettingsButtonCommand command,
                                                             long chatId,
                                                             int messageId,
                                                             Locale locale){
        String answer = messageSource.getMessage("settings.answer",
                Collections.singleton(locale.getLanguage()).toArray(),
                locale);
        return createEditMessage(answer, chatId, messageId);
    }


    private EditMessageText createEditMessage(String answer,
                                              long chatId,
                                              int messageId) {
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        editMessage.setText(answer);
        editMessage.setMessageId(messageId);
        return editMessage;
    }
}
