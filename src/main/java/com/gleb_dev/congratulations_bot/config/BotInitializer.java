package com.gleb_dev.congratulations_bot.config;

import com.gleb_dev.congratulations_bot.service.CongratulationsBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Class initializes telegram bot
 */

@Component
@Slf4j
public class BotInitializer {

    @Autowired
    CongratulationsBot congratulationsBot;

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(congratulationsBot);
        }
        catch (TelegramApiException e){
            log.error("Error: " + e.getMessage());
        }
    }
}
