package com.gleb_dev.congratulations_bot.service.handler;

import com.gleb_dev.congratulations_bot.constant.LanguageConstants;
import com.gleb_dev.congratulations_bot.constant.callbackButton.HolidayButtonCommand;
import com.gleb_dev.congratulations_bot.constant.callbackButton.SettingsButtonCommand;
import com.gleb_dev.congratulations_bot.constant.callbackButton.VideoButtonCommand;
import com.gleb_dev.congratulations_bot.entity.Language;
import com.gleb_dev.congratulations_bot.entity.User;
import com.gleb_dev.congratulations_bot.repository.UserRepository;
import com.gleb_dev.congratulations_bot.service.KeyboardProvider;
import com.gleb_dev.congratulations_bot.service.UserService;
import com.gleb_dev.congratulations_bot.service.YouTubeSearchProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    private UserService userService;

    @Autowired
    public CallbackQueryHandler(YouTubeSearchProvider youTubeSearchProvider,
                                MessageSource messageSource,
                                KeyboardProvider keyboardProvider,
                                UserService userService) {
        this.youTubeSearchProvider = youTubeSearchProvider;
        this.messageSource = messageSource;
        this.keyboardProvider = keyboardProvider;
        this.userService = userService;
    }

    /**
     * Method processes incoming queries
     * @param callbackQuery query that needs to be processed
     * @return response to query received
     */
    public List<BotApiMethod<?>> processCallbackQuery(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        long chatId = callbackQuery.getMessage().getChatId();
        int messageId = callbackQuery.getMessage().getMessageId();
        List<BotApiMethod<?>> responseList = new ArrayList<>();

        User currUser = userService.getUser(chatId);

        Language language;
        if(currUser != null){
            language = currUser.getLanguage();
        }
        else {
            language = LanguageConstants.DEFAULT_LANGUAGE;
        }

        Locale locale = Locale.forLanguageTag(language.getLanguageTag());

        log.info("User \"{}\" sent callback query with data: {}",
                callbackQuery.getMessage().getChat().getFirstName(),
                data);

        VideoButtonCommand videoCommand = VideoButtonCommand.valueOfCommandName(data);
        if (videoCommand != null) {
            responseList.add(handleVideoCommand(videoCommand, chatId, messageId, locale));
            return responseList;
        }

        HolidayButtonCommand holidayCommand = HolidayButtonCommand.valueOfCommandName(data);
        if(holidayCommand != null){
            responseList.add(createEditMessage(messageSource.getMessage(holidayCommand.getCongratulationCode(),
                    null,
                    locale), chatId, messageId));
            return responseList;
        }

        SettingsButtonCommand settingsCommand = SettingsButtonCommand.valueOfCommandName(data);
        if(settingsCommand != null){
            return handleSettingsCommand(settingsCommand, chatId, messageId,currUser, locale);
        }

        responseList.add(createEditMessage(messageSource.getMessage(LanguageConstants.COMMAND_NOT_FOUND_CODE, null, locale),
                chatId, messageId));
        return responseList;
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

    private List<BotApiMethod<?>> handleSettingsCommand(SettingsButtonCommand command,
                                                                  long chatId,
                                                                  int messageId,
                                                                  User user,
                                                                  Locale locale){
        List<BotApiMethod<?>> responseList = new ArrayList<>();

        if (user == null){
            responseList.add(createEditMessage(messageSource.getMessage(LanguageConstants.USER_NOT_REGISTERED_CODE,
                    null, locale), chatId, messageId));
            return responseList;
        }

        if (command == SettingsButtonCommand.LANGUAGE_RU){
            user.setLanguage(Language.RUSSIAN);
        } else if(command == SettingsButtonCommand.LANGUAGE_EN){
            user.setLanguage(Language.ENGLISH);
        }
        userService.updateUser(user);

        Locale newLocale = Locale.forLanguageTag(user.getLanguage().getLanguageTag());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messageSource.getMessage(LanguageConstants.SETTING_KEYBOARD_CHANGED, null, newLocale));
        sendMessage.setReplyMarkup(keyboardProvider.getDefaultKeyboard(newLocale));
        responseList.add(sendMessage);

        String answer = messageSource.getMessage(LanguageConstants.SETTINGS_ANSWER,
                Collections.singleton(messageSource.getMessage(command.getTextCode(), null, newLocale)).toArray(),
                newLocale);
        responseList.add(createEditMessage(answer, chatId, messageId));

        return responseList;
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
