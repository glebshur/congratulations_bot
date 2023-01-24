package com.gleb_dev.congratulations_bot.service.handler;

import com.gleb_dev.congratulations_bot.constant.callbackButton.HolidayButtonCommand;
import com.gleb_dev.congratulations_bot.constant.callbackButton.VideoButtonCommand;
import com.gleb_dev.congratulations_bot.service.YouTubeSearchProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.io.Serializable;

/**
 * Class that process callback queries
 */

@Component
@Slf4j
public class CallbackQueryHandler {


    private YouTubeSearchProvider youTubeSearchProvider;

    @Autowired
    public CallbackQueryHandler(YouTubeSearchProvider youTubeSearchProvider) {
        this.youTubeSearchProvider = youTubeSearchProvider;
    }


    /**
     * Method processes incoming queries
     * @param callbackQuery query that needs to be processed
     * @return response to query received
     */
    public BotApiMethod<Serializable> processCallbackQuery(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();

        log.info("User \"{}\" sent callback query with data: {}",
                callbackQuery.getMessage().getChat().getFirstName(),
                data);

        long chatId = callbackQuery.getMessage().getChatId();
        int messageId = callbackQuery.getMessage().getMessageId();

        VideoButtonCommand videoCommand = VideoButtonCommand.valueOfCommandName(data);
        if (videoCommand != null) {
            return handleVideoCommand(videoCommand, chatId, messageId);
        }

        HolidayButtonCommand holidayCommand = HolidayButtonCommand.valueOfCommandName(data);
        if(holidayCommand != null){
            return createEditMessage(holidayCommand.getCongratulation(), chatId, messageId);
        }

        return createEditMessage("Не понимаю тебя", chatId, messageId);
    }

    private BotApiMethod<Serializable> handleVideoCommand(VideoButtonCommand command,
                                                          long chatId,
                                                          int messageId) {
        String answer = youTubeSearchProvider.getOneRandomVideoLink(command.getSearchQuery(), 10);
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
