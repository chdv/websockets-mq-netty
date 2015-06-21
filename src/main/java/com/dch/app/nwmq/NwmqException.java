package com.dch.app.nwmq;

/**
 * Created by ִלטענטי on 21.06.2015.
 */
public class NwmqException extends RuntimeException {

    public NwmqException(String message) {
        super(message);
    }

    public NwmqException(String message, Throwable cause) {
        super(message, cause);
    }

    public NwmqException(Throwable cause) {
        super(cause);
    }
}
