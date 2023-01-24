package com.gleb_dev.congratulations_bot.repository;

import com.gleb_dev.congratulations_bot.entity.Joke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for interaction with joke's entity in the database
 */

@Repository
public interface JokeRepository extends JpaRepository<Joke, Long> {
}
