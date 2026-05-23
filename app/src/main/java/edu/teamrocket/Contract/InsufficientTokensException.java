package edu.teamrocket.Contract;

public class InsufficientTokensException extends RuntimeException {

    public InsufficientTokensException () {
        super();
    }

    public InsufficientTokensException(String message) {
        super(message);
    }
}