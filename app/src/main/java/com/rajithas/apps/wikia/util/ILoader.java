package com.rajithas.apps.wikia.util;

import com.rajithas.apps.wikia.task.ITask;

/**
 * Created by rajitha.siriwardena on 15-Feb-15.
 */
public interface ILoader {
    <R, T> void load(ITask<R, T> task, ICallback<R> callback, T input);
}
