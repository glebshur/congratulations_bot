package com.gleb_dev.congratulations_bot.service.handler;

import com.gleb_dev.congratulations_bot.constant.ButtonCommand;
import com.gleb_dev.congratulations_bot.constant.LanguageConstants;
import com.gleb_dev.congratulations_bot.constant.MenuCommand;
import com.gleb_dev.congratulations_bot.constant.callbackButton.HolidayButtonCommand;
import com.gleb_dev.congratulations_bot.constant.callbackButton.SettingsButtonCommand;
import com.gleb_dev.congratulations_bot.constant.callbackButton.VideoButtonCommand;
import com.gleb_dev.congratulations_bot.entity.Joke;
import com.gleb_dev.congratulations_bot.entity.Language;
import com.gleb_dev.congratulations_bot.entity.Wish;
import com.gleb_dev.congratulations_bot.exception.JokeNotFoundException;
import com.gleb_dev.congratulations_bot.exception.WishNotFoundException;
import com.gleb_dev.congratulations_bot.service.ButtonCommandTranslationProvider;
import com.gleb_dev.congratulations_bot.service.JokeService;
import com.gleb_dev.congratulations_bot.service.KeyboardProvider;
import com.gleb_dev.congratulations_bot.service.WishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.*;

/**
 * Class that process messages
 */

@Component
@Slf4j
public class MessageHandler {

    private JokeService jokeService;
    private WishService wishService;
    private MessageSource messageSource;
    private KeyboardProvider keyboardProvider;
    private ButtonCommandTranslationProvider buttonTranslationProvider;

//    private static final String SETTINGS_ANSWER_CODE = "menuCommand.setting.answer";
//    private static final String HELP_ANSWER_CODE = "menuCommand.help.answer";
//    private static final String START_ANSWER_CODE = "menuCommand.start.answer";

    @Autowired
    public MessageHandler(JokeService jokeService,
                          WishService wishService,
                          MessageSource messageSource,
                          KeyboardProvider keyboardProvider,
                          ButtonCommandTranslationProvider buttonTranslationProvider) {
        this.jokeService = jokeService;
        this.wishService = wishService;
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
        String answer = null;
        ReplyKeyboard replyKeyboard = null;

        Language language = LanguageConstants.DEFAULT_LANGUAGE;

        Locale locale = Locale.forLanguageTag(language.getLanguageTag());
        if (message.hasText()) {

            log.info("User \"{}\" sent message: {}",
                    message.getChat().getFirstName(),
                    message.getText());

            String inputText = message.getText();

            if (inputText.equals(MenuCommand.START.getCommand())) {
                answer = processMenuCommand(MenuCommand.START, locale, message.getChat().getFirstName());
                replyKeyboard = keyboardProvider.getDefaultKeyboard(locale);

            } else if (inputText.equals(MenuCommand.SETTINGS.getCommand())) {
                answer = processMenuCommand(MenuCommand.SETTINGS, locale,
                        messageSource.getMessage(language.getTextCode(), null, locale));
                replyKeyboard = keyboardProvider.getInlineKeyboard(SettingsButtonCommand.values(), locale);

            } else if (inputText.equals(MenuCommand.HELP.getCommand())) {
                answer = processMenuCommand(MenuCommand.HELP, locale, getCommandsDescriptionList(locale));

            } else {
                ButtonCommand buttonCommand = buttonTranslationProvider.getButtonCommand(inputText);

                if (buttonCommand == ButtonCommand.GET_VIDEO) {
                    answer = messageSource.getMessage(LanguageConstants.GET_VIDEO_ANSWER_CODE,
                            null, locale);
                    replyKeyboard = keyboardProvider.getInlineKeyboard(VideoButtonCommand.values(), locale);

                } else if (buttonCommand == ButtonCommand.CHOOSE_HOLIDAY) {
                    answer = messageSource.getMessage(LanguageConstants.CHOOSE_HOLIDAY_ANSWER_CODE,
                            null, locale);
                    replyKeyboard = keyboardProvider.getInlineKeyboard(HolidayButtonCommand.values(), locale);

                } else if (buttonCommand == ButtonCommand.GET_WISH) {

                    answer = processGetWishCommand();
                } else if (buttonCommand == ButtonCommand.GET_JOKE) {

                    answer = processGetJokeCommand();
                }
            }
        }

        if (answer == null) {
            answer = processCommandNotFound(locale);
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(answer);
        sendMessage.setReplyMarkup(replyKeyboard);

        return sendMessage;
    }

    private String processMenuCommand(MenuCommand command, Locale locale, String... args){
        return messageSource.getMessage(command.getAnswerCode(), args, locale);
    }

//    private String processStartCommand(String name, Locale locale) {
//        String answer = messageSource.getMessage(START_ANSWER_CODE,
//                Collections.singleton(name).toArray(),
//                locale);
//        return answer;
//    }

    private String processCommandNotFound(Locale locale) {
        String answer = messageSource.getMessage(LanguageConstants.COMMAND_NOT_FOUND_CODE,
                null,
                locale);
        return answer;
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

}
