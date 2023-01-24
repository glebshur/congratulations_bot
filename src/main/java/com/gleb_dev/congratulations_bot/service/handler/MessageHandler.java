package com.gleb_dev.congratulations_bot.service.handler;

import com.gleb_dev.congratulations_bot.constant.ButtonCommand;
import com.gleb_dev.congratulations_bot.constant.MenuCommand;
import com.gleb_dev.congratulations_bot.constant.callbackButton.CallbackButton;
import com.gleb_dev.congratulations_bot.constant.callbackButton.HolidayButtonCommand;
import com.gleb_dev.congratulations_bot.constant.callbackButton.VideoButtonCommand;
import com.gleb_dev.congratulations_bot.entity.Joke;
import com.gleb_dev.congratulations_bot.entity.Wish;
import com.gleb_dev.congratulations_bot.exception.JokeNotFoundException;
import com.gleb_dev.congratulations_bot.exception.WishNotFoundException;
import com.gleb_dev.congratulations_bot.service.JokeService;
import com.gleb_dev.congratulations_bot.service.WishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class that process messages
 */

@Component
@Slf4j
public class MessageHandler {

    private JokeService jokeService;
    private WishService wishService;

    @Autowired
    public MessageHandler(JokeService jokeService, WishService wishService) {
        this.jokeService = jokeService;
        this.wishService = wishService;
    }

    /**
     * Method processes incoming messages
     * @param message message that needs to be processed
     * @return response to message received
     */
    public SendMessage processMessage(Message message) {
        String answer = "Прости, я тебя не понял";
        ReplyKeyboard replyKeyboard = null;

        if (message.hasText()) {

            log.info("User \"{}\" sent message: {}",
                    message.getChat().getFirstName(),
                    message.getText());

            String inputText = message.getText();
            if (inputText.equals(MenuCommand.START.getCommand())) {
                answer = "Привет, " + message.getChat().getFirstName() + "!!!";
                replyKeyboard = getDefaultKeyboard();
//            } else if (inputText.equals(MenuCommand.SETTINGS.getCommand())) {
//                answer = "Вот твои настроки";
            } else if (inputText.equals(MenuCommand.HELP.getCommand())) {
                answer = getHelp();
            } else if (inputText.equals(ButtonCommand.GET_JOKE.getCommand())) {
                answer = processGetJokeCommand();
            } else if (inputText.equals(ButtonCommand.GET_VIDEO.getCommand())) {
                answer = "Выбери категорию";
                replyKeyboard = getInlineKeyboard(VideoButtonCommand.values());
            } else if (inputText.equals(ButtonCommand.GET_WISH.getCommand())) {
                answer = processGetWishCommand();
            } else if (inputText.equals(ButtonCommand.CHOOSE_HOLIDAY.getCommand())) {
                answer = "Выбери праздник!";
                replyKeyboard = getInlineKeyboard(HolidayButtonCommand.values());
            }

        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(answer);
        sendMessage.setReplyMarkup(replyKeyboard);

        return sendMessage;
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


    private String getHelp() {
        StringBuilder help = new StringBuilder();
        help.append("Это CongratulationsBot, который может поздравлять с праздниками, отправлять случайный анекдот, " +
                "видео или пожелания :)\n\n" +
                "Создания самого бота приурочено ко дню рождения моем мамы. Мам, спасибо тебе за все, что ты делаешь!\n\n" +
                "Вот полный список команд поддерживаемых ботом:\n\n");
        Arrays.stream(MenuCommand.values())
                .forEach(menuCommand -> help.append(menuCommand.getCommand()).append(" - ")
                        .append(menuCommand.getFullDescription()).append("\n"));
        help.append("\nОстальное взаимодействие поддерживается с помощью кнопок");

        return help.toString();
    }

    private ReplyKeyboardMarkup getDefaultKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add(ButtonCommand.CHOOSE_HOLIDAY.getCommand());
        row.add(ButtonCommand.GET_WISH.getCommand());
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add(ButtonCommand.GET_JOKE.getCommand());
        row.add(ButtonCommand.GET_VIDEO.getCommand());
        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);
        return keyboardMarkup;
    }

    private InlineKeyboardMarkup getInlineKeyboard(CallbackButton[] buttons) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        Arrays.stream(buttons)
                .forEach(button -> keyboard.add(getSingleButtonInRow(button.getText(), button.getCommandName())));
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
