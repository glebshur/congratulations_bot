package com.gleb_dev.congratulations_bot.service;

import com.gleb_dev.congratulations_bot.constant.LanguageConstants;
import com.gleb_dev.congratulations_bot.constant.MenuCommand;
import com.gleb_dev.congratulations_bot.config.BotConfig;
import com.gleb_dev.congratulations_bot.entity.Language;
import com.gleb_dev.congratulations_bot.service.handler.CallbackQueryHandler;
import com.gleb_dev.congratulations_bot.service.handler.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Main bot class
 */

@Component
@Slf4j
public class CongratulationsBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private MessageHandler messageHandler;
    private CallbackQueryHandler callbackQueryHandler;
    private MessageSource messageSource;

    @Autowired
    public CongratulationsBot(BotConfig botConfig,
                              MessageHandler messageHandler,
                              CallbackQueryHandler callbackQueryHandler,
                              MessageSource messageSource) {
        this.botConfig = botConfig;
        this.messageHandler = messageHandler;
        this.callbackQueryHandler = callbackQueryHandler;
        this.messageSource = messageSource;
        configureCommandMenu();
    }

    @Override
    public String getBotUsername() {
        return botConfig.getName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            SendMessage message = messageHandler.processMessage(update.getMessage());

            try {
                execute(message);
            } catch (TelegramApiException e) {
                log.error("Error: " + e.getMessage());
            }
        }
        else if(update.hasCallbackQuery()){
            BotApiMethod<Serializable> message = callbackQueryHandler.processCallbackQuery(update.getCallbackQuery());

            try {
                execute(message);
            } catch (TelegramApiException e) {
                log.error("Error: " + e.getMessage());
            }
        }
    }

    private void configureCommandMenu() {
        Locale locale = Locale.forLanguageTag(LanguageConstants.DEFAULT_LANGUAGE.getLanguageTag());
        List<BotCommand> botCommandList = Arrays.stream(MenuCommand.values())
                .map(menuCommand -> new BotCommand(menuCommand.getCommand(),
                        messageSource.getMessage(menuCommand.getShortDescriptionCode(), null, locale)))
                .collect(Collectors.toList());

        try {
            execute(new SetMyCommands(botCommandList, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error: " + e.getMessage());
        }
    }

}
