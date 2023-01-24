package com.gleb_dev.congratulations_bot.repository;

import com.gleb_dev.congratulations_bot.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for interaction with wish's entity in the database
 */

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
}
