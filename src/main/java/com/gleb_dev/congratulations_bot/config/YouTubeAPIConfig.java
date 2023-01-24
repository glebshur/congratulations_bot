package com.gleb_dev.congratulations_bot.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Class configures youtube api
 */

@Configuration
@Data
@PropertySource("classpath:application.properties")
public class YouTubeAPIConfig {

    @Value("${youtube.key}")
    private String key;

    @Value("${application.name}")
    private String applicationName;

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    @Bean
    public YouTube YouTube() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(applicationName)
                .build();
    }
}
