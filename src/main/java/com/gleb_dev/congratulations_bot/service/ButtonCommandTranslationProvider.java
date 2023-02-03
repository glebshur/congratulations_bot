package com.gleb_dev.congratulations_bot.service;

import com.gleb_dev.congratulations_bot.constant.ButtonCommand;
import com.gleb_dev.congratulations_bot.entity.Language;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Locale;

@Component
@Slf4j
public class ButtonCommandTranslationProvider {

    private HashMap<String, ButtonCommand> buttonsTranslationMap;
    private MessageSource messageSource;

    @Autowired
    public ButtonCommandTranslationProvider(MessageSource messageSource) {
        this.messageSource = messageSource;
        buttonsTranslationMap = null;
    }

    private void initMapByMessageProperties(){
        buttonsTranslationMap = new HashMap<>();

        for (Language language : Language.values()){
            Locale locale = Locale.forLanguageTag(language.getLanguageTag());
            for (ButtonCommand buttonCommand : ButtonCommand.values()){
                buttonsTranslationMap.put(messageSource.getMessage(buttonCommand.getCommandCode(), null, locale),
                        buttonCommand);
            }
        }

        log.info("buttonsTranslationMap was inited");
    }

    public ButtonCommand getButtonCommand(String command){
        if(buttonsTranslationMap == null){
            initMapByMessageProperties();
        }

        return buttonsTranslationMap.get(command);
    }
}
