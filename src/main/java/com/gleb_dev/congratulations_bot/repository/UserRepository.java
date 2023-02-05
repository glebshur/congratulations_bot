package com.gleb_dev.congratulations_bot.repository;

import com.gleb_dev.congratulations_bot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for interaction with user's entity in the database
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
