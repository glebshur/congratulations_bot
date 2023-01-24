package com.gleb_dev.congratulations_bot.exception;

public class WishNotFoundException extends RuntimeException {
    public WishNotFoundException(String message) {
        super(message);
    }
}
