package com.gleb_dev.congratulations_bot.repository;

import com.gleb_dev.congratulations_bot.entity.Joke;
import com.gleb_dev.congratulations_bot.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface for interaction with joke's entity in the database
 */

@Repository
public interface JokeRepository extends JpaRepository<Joke, Long> {

    public List<Joke> findAllByLanguage(Language language);
}
