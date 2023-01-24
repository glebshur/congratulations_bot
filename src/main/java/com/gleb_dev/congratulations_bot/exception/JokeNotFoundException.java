package com.gleb_dev.congratulations_bot.exception;

public class JokeNotFoundException extends RuntimeException {
    public JokeNotFoundException(String message) {
        super(message);
    }
}
