package com.gleb_dev.congratulations_bot.service.handler;

import com.gleb_dev.congratulations_bot.constant.ButtonCommand;
import com.gleb_dev.congratulations_bot.constant.LanguageConstants;
import com.gleb_dev.congratulations_bot.constant.MenuCommand;
import com.gleb_dev.congratulations_bot.constant.callbackButton.HolidayButtonCommand;
import com.gleb_dev.congratulations_bot.constant.callbackButton.SettingsButtonCommand;
import com.gleb_dev.congratulations_bot.constant.callbackButton.VideoButtonCommand;
import com.gleb_dev.congratulations_bot.entity.Joke;
import com.gleb_dev.congratulations_bot.entity.Language;
import com.gleb_dev.congratulations_bot.entity.User;
import com.gleb_dev.congratulations_bot.entity.Wish;
import com.gleb_dev.congratulations_bot.exception.JokeNotFoundException;
import com.gleb_dev.congratulations_bot.exception.WishNotFoundException;
import com.gleb_dev.congratulations_bot.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.*;

/**
 * Class that process messages
 */

@Component
@Slf4j
public class MessageHandler {

    private JokeService jokeService;
    private WishService wishService;
    private UserService userService;
    private MessageSource messageSource;
    private KeyboardProvider keyboardProvider;
    private ButtonCommandTranslationProvider buttonTranslationProvider;

    @Autowired
    public MessageHandler(JokeService jokeService,
                          WishService wishService,
                          UserService userService,
                          MessageSource messageSource,
                          KeyboardProvider keyboardProvider,
                          ButtonCommandTranslationProvider buttonTranslationProvider) {
        this.jokeService = jokeService;
        this.wishService = wishService;
        this.userService = userService;
        this.messageSource = messageSource;
        this.keyboardProvider = keyboardProvider;
        this.buttonTranslationProvider = buttonTranslationProvider;
    }

    /**
     * Method processes incoming messages
     *
     * @param message message that needs to be processed
     * @return response to message received
     */
    public SendMessage processMessage(Message message) {
        SendMessage sendMessage = null;
        long chatId = message.getChatId();
        User currUser = userService.getUser(chatId);

        Language language;
        if(currUser != null){
            language = currUser.getLanguage();
        }
        else {
            language = LanguageConstants.DEFAULT_LANGUAGE;
        }

        Locale locale = Locale.forLanguageTag(language.getLanguageTag());
        if (message.hasText()) {

            log.info("User \"{}\" sent message: {}",
                    message.getChat().getFirstName(),
                    message.getText());

            String inputText = message.getText();

            if (inputText.equals(MenuCommand.START.getCommand())) {
                sendMessage = processStartCommand(chatId, message.getChat().getFirstName(), locale);

            } else if (inputText.equals(MenuCommand.SETTINGS.getCommand())) {
                sendMessage = processSettingsCommand(chatId, locale, currUser);

            } else if (inputText.equals(MenuCommand.HELP.getCommand())) {
                sendMessage = createSendMessage(chatId,
                        getMenuCommandAnswer(MenuCommand.HELP, locale, getCommandsDescriptionList(locale)),
                null);

            } else if (inputText.equals(MenuCommand.REGISTRATION.getCommand())) {
                sendMessage = processRegistrationCommand(chatId, locale, currUser, message);

            } else if (inputText.equals(MenuCommand.MY_DATA.getCommand())) {
                sendMessage = processMyDataCommand(chatId, locale, currUser);

            } else if (inputText.equals(MenuCommand.DELETE_DATA.getCommand())) {
                sendMessage = processDeleteDataCommand(chatId, locale, currUser);

            } else {
                ButtonCommand buttonCommand = buttonTranslationProvider.getButtonCommand(inputText);

                if (buttonCommand == ButtonCommand.GET_VIDEO) {
                    sendMessage = processGetVideoCommand(chatId, locale);

                } else if (buttonCommand == ButtonCommand.CHOOSE_HOLIDAY) {
                    sendMessage = processChooseHolidayCommand(chatId, locale);

                } else if (buttonCommand == ButtonCommand.GET_WISH) {
                    sendMessage = createSendMessage(chatId,processGetWishCommand(), null);

                } else if (buttonCommand == ButtonCommand.GET_JOKE) {
                    sendMessage = createSendMessage(chatId, processGetJokeCommand(), null);

                }
            }
        }

        if (sendMessage == null) {
            sendMessage = processDefaultMessage(chatId, locale);
        }

        return sendMessage;
    }

    private SendMessage processDefaultMessage(long chatId, Locale locale){
        return createSendMessage(chatId, processCommandNotFound(locale), null);
    }

    private SendMessage processStartCommand(long chatId, String name, Locale locale) {
        return createSendMessage(chatId,
                getMenuCommandAnswer(MenuCommand.START, locale, name),
                keyboardProvider.getDefaultKeyboard(locale));
    }

    private SendMessage processSettingsCommand(long chatId, Locale locale, User user){
        if(user == null){
            return createSendMessage(chatId, processUserNotRegistered(locale), null);
        }

        return createSendMessage(chatId,
                getMenuCommandAnswer(MenuCommand.SETTINGS, locale,
                        messageSource.getMessage(user.getLanguage().getTextCode(), null, locale)),
                keyboardProvider.getInlineKeyboard(SettingsButtonCommand.values(), locale));
    }

    private SendMessage processRegistrationCommand(long chatId, Locale locale, User currUser, Message message){
        if(currUser != null){
            return createSendMessage(chatId, messageSource.getMessage(LanguageConstants.USER_ALREADY_REGISTERED,
                    null, locale), null);
        }

        User user = new User(chatId, message.getChat().getFirstName(), LanguageConstants.DEFAULT_LANGUAGE);
        userService.createUser(user);

        return createSendMessage(chatId, getMenuCommandAnswer(MenuCommand.REGISTRATION, locale), null);
    }

    private SendMessage processMyDataCommand(long chatId, Locale locale, User user){
        if(user == null){
            return createSendMessage(chatId, processUserNotRegistered(locale), null);
        }

        return createSendMessage(chatId,
                getMenuCommandAnswer(MenuCommand.MY_DATA, locale, user.getId().toString(), user.getFirstName(),
                        messageSource.getMessage(user.getLanguage().getTextCode(), null, locale)),
                null);
    }

    private SendMessage processDeleteDataCommand(long chatId, Locale locale, User user){
        if(user == null){
            return createSendMessage(chatId, processUserNotRegistered(locale), null);
        }

        userService.deleteUser(user);
        return createSendMessage(chatId, getMenuCommandAnswer(MenuCommand.DELETE_DATA, locale),
                keyboardProvider.getDefaultKeyboard(
                        Locale.forLanguageTag(LanguageConstants.DEFAULT_LANGUAGE.getLanguageTag())));
    }

    private SendMessage processGetVideoCommand(long chatId, Locale locale){
        return createSendMessage(chatId,
                messageSource.getMessage(LanguageConstants.GET_VIDEO_ANSWER_CODE, null, locale),
                keyboardProvider.getInlineKeyboard(VideoButtonCommand.values(), locale));
    }

    private SendMessage processChooseHolidayCommand(long chatId, Locale locale){
        return createSendMessage(chatId,
                messageSource.getMessage(LanguageConstants.CHOOSE_HOLIDAY_ANSWER_CODE, null, locale),
                keyboardProvider.getInlineKeyboard(HolidayButtonCommand.values(), locale));
    }

    private String processCommandNotFound(Locale locale) {
        return messageSource.getMessage(LanguageConstants.COMMAND_NOT_FOUND_CODE, null, locale);
    }

    private String processUserNotRegistered(Locale locale){
        return messageSource.getMessage(LanguageConstants.USER_NOT_REGISTERED_CODE,null, locale);
    }

    private String processGetWishCommand() {
        String answer = "Прости, я не нашел пожеланий";

        try {
            Wish wish = wishService.getRandomWish();
            answer = wish.getText();
        } catch (WishNotFoundException e) {
            log.error("Error: " + e.getMessage());
        }

        return answer;
    }

    private String processGetJokeCommand() {
        String answer = "Прости, я не нашел анекдот";

        try {
            Joke joke = jokeService.getRandomJoke();
            answer = "Вот случайный анекдот:\n\n" + "**" + joke.getTitle() + "**\n" + joke.getText();
        } catch (JokeNotFoundException e) {
            log.error("Error: " + e.getMessage());
        }

        return answer;
    }


    private String getCommandsDescriptionList(Locale locale) {
        StringBuilder help = new StringBuilder();
        Arrays.stream(MenuCommand.values())
                .forEach(menuCommand -> help
                        .append(menuCommand.getCommand())
                        .append(" - ")
                        .append(messageSource.getMessage(menuCommand.getFullDescriptionCode(),
                                null, locale))
                        .append("\n"));

        return help.toString();
    }

    private String getMenuCommandAnswer(MenuCommand command, Locale locale, String... args){
        return messageSource.getMessage(command.getAnswerCode(), args, locale);
    }

    private SendMessage createSendMessage(long chatId, String text, ReplyKeyboard keyboardMarkup){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

}
