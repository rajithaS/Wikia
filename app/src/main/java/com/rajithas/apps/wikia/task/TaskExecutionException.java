package com.rajithas.apps.wikia.task;

/**
 * Created by rajitha.siriwardena on 15-Feb-15.
 */
public class TaskExecutionException extends Exception {

    public TaskExecutionException(String message) {
        super(message);
    }

    public TaskExecutionException(Throwable throwable) {
        super(throwable);
    }
}
