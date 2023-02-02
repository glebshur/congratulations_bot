package com.gleb_dev.congratulations_bot.service.handler;

import com.gleb_dev.congratulations_bot.constant.ButtonCommand;
import com.gleb_dev.congratulations_bot.constant.MenuCommand;
import com.gleb_dev.congratulations_bot.constant.callbackButton.CallbackButton;
import com.gleb_dev.congratulations_bot.constant.callbackButton.HolidayButtonCommand;
import com.gleb_dev.congratulations_bot.constant.callbackButton.SettingsButtonCommand;
import com.gleb_dev.congratulations_bot.constant.callbackButton.VideoButtonCommand;
import com.gleb_dev.congratulations_bot.entity.Joke;
import com.gleb_dev.congratulations_bot.entity.Wish;
import com.gleb_dev.congratulations_bot.exception.JokeNotFoundException;
import com.gleb_dev.congratulations_bot.exception.WishNotFoundException;
import com.gleb_dev.congratulations_bot.service.JokeService;
import com.gleb_dev.congratulations_bot.service.KeyboardProvider;
import com.gleb_dev.congratulations_bot.service.WishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

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

    @Autowired
    public MessageHandler(JokeService jokeService, WishService wishService, MessageSource messageSource, KeyboardProvider keyboardProvider) {
        this.jokeService = jokeService;
        this.wishService = wishService;
        this.messageSource = messageSource;
        this.keyboardProvider = keyboardProvider;
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

        String languageTag = "en";
        Locale locale = Locale.forLanguageTag(languageTag);
        if (message.hasText()) {

            log.info("User \"{}\" sent message: {}",
                    message.getChat().getFirstName(),
                    message.getText());

            String inputText = message.getText();
            if (inputText.equals(MenuCommand.START.getCommand())) {
                answer = processStartCommand(message.getChat().getFirstName(), locale);
                replyKeyboard = keyboardProvider.getDefaultKeyboard(locale);
            } else if (inputText.equals(MenuCommand.SETTINGS.getCommand())) {
                answer = messageSource.getMessage("menuCommand.settings.answer",
                        null, locale);
                replyKeyboard = keyboardProvider.getInlineKeyboard(SettingsButtonCommand.values(), locale);
            } else if (inputText.equals(MenuCommand.HELP.getCommand())) {
                answer = processHelpButton(locale);
            } else if (inputText.equals(ButtonCommand.GET_VIDEO.getCommandCode())) {
                answer = messageSource.getMessage("buttonCommand.getVideo.answer",
                        null, locale);
                replyKeyboard = keyboardProvider.getInlineKeyboard(VideoButtonCommand.values(), locale);
            } else if (inputText.equals(ButtonCommand.CHOOSE_HOLIDAY.getCommandCode())) {
                answer = messageSource.getMessage("buttonCommand.chooseHoliday.answer",
                        null, locale);
                replyKeyboard = keyboardProvider.getInlineKeyboard(HolidayButtonCommand.values(), locale);
            } else if (inputText.equals(ButtonCommand.GET_WISH.getCommandCode())) {
                answer = processGetWishCommand();
            } else if (inputText.equals(ButtonCommand.GET_JOKE.getCommandCode())) {
                answer = processGetJokeCommand();
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

    private String processStartCommand(String name, Locale locale) {
        String answer = messageSource.getMessage("menuCommand.start.answer",
                Collections.singleton(name).toArray(),
                locale);
        return answer;
    }

    private String processCommandNotFound(Locale locale) {
        String answer = messageSource.getMessage("commandNotFound",
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

    private String processHelpButton(Locale locale) {
        StringBuilder help = new StringBuilder();
        Arrays.stream(MenuCommand.values())
                .forEach(menuCommand -> help
                        .append(menuCommand.getCommand())
                        .append(" - ")
                        .append(messageSource.getMessage(menuCommand.getFullDescriptionCode(),
                                null, locale))
                        .append("\n"));

        String answer = messageSource.getMessage("menuCommand.help.answer",
                Collections.singleton(help.toString()).toArray(),
                locale);

        return answer;
    }

}
