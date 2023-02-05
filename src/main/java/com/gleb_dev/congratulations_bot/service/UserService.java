package com.gleb_dev.congratulations_bot.service;

import com.gleb_dev.congratulations_bot.entity.User;
import com.gleb_dev.congratulations_bot.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class implements main logic of user's entity
 */

@Service
@Slf4j
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(long id){
        return userRepository.findById(id)
                .orElse(null);
    }

    public User createUser(User user){
        log.info("User is creating with id {} and first name {}", user.getId(), user.getFirstName());
        return userRepository.save(user);
    }

    public User updateUser(User user){
        log.info("User is updating with id {} and first name {}", user.getId(), user.getFirstName());
        return userRepository.save(user);
    }

    public void deleteUser(User user){
        log.info("User is deleting with id {} and first name {}", user.getId(), user.getFirstName());
        userRepository.delete(user);
    }
}
