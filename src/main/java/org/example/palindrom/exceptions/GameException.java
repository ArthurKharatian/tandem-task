package org.example.palindrom.exceptions;

public class GameException extends RuntimeException {

    public GameException() {
    }

    public GameException(String message) {
        super(message);
    }
}
