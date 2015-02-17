package com.rajithas.apps.wikia.network;

/**
 * Created by rajitha.siriwardena on 15-Feb-15.
 */
public class NetworkException extends Exception {

    public NetworkException(String message) {
        super(message);
    }

    public NetworkException(Throwable throwable) {
        super(throwable);
    }
}
