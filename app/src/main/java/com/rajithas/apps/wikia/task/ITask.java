package com.rajithas.apps.wikia.task;

/**
 * Created by rajitha.siriwardena on 15-Feb-15.
 */
public interface ITask<R, T> {
    R execute(T input) throws TaskExecutionException;
}
