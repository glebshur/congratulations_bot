package com.gleb_dev.congratulations_bot.service;

import com.gleb_dev.congratulations_bot.config.YouTubeAPIConfig;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Class that allows to search videos on YouTube
 */

@Component
@Slf4j
public class YouTubeSearchProvider {

    private YouTube youTube;
    private YouTubeAPIConfig youTubeAPIConfig;
    private Random random;

    @Autowired
    public YouTubeSearchProvider(YouTube youTube, YouTubeAPIConfig youTubeAPIConfig) {
        this.youTube = youTube;
        this.youTubeAPIConfig = youTubeAPIConfig;
        this.random = new Random();
    }

    /**
     * Method searches videos on YouTube by query and returns links
     */
    public List<String> searchVideosByQuery(String query, long count) {

        List<String> links = null;
        try {
            YouTube.Search.List request = youTube.search()
                    .list("snippet");

            SearchListResponse response = request.setKey(youTubeAPIConfig.getKey())
                    .setMaxResults(count)
                    .setQ(query)
                    .setType("video")
                    .execute();

            links = response.getItems().stream()
                    .map(item -> "https://www.youtube.com/watch?v=" + item.getId().getVideoId())
                    .collect(Collectors.toList());

        } catch (IOException e) {
            log.error("Error: " + e.getMessage());
        }

        return links;
    }

    /**
     * Method searches videos on YouTube by query and returns one random link
     */
    public String getOneRandomVideoLink(String query, long count) {
        List<String> links = searchVideosByQuery(query, count);
        return links.get(random.nextInt(links.size()));
    }
}
