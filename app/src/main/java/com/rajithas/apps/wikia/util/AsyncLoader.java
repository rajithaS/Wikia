package com.rajithas.apps.wikia.util;

import android.os.AsyncTask;

import com.rajithas.apps.wikia.task.ITask;
import com.rajithas.apps.wikia.task.TaskExecutionException;

import javax.inject.Inject;

/**
 * Created by rajitha.siriwardena on 15-Feb-15.
 */
public class AsyncLoader implements ILoader {

    @Inject
    public AsyncLoader() {
    }

    @Override
    public <R, T> void load(ITask<R, T> task, ICallback<R> callback, T input) {
        new AyncLoaderTask<>(task, callback).execute(input);
    }

    private class AyncLoaderTask<R, T> extends AsyncTask<T, Void, R> {
        private final ITask<R, T> task;
        private final ICallback<R> callback;

        private AyncLoaderTask(ITask<R, T> task, ICallback<R> callback) {
            this.task = task;
            this.callback = callback;
        }

        @SafeVarargs
        @Override
        protected final R doInBackground(T... params) {
            try {
                return task.execute(params[0]);
            } catch (TaskExecutionException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(R r) {
            super.onPostExecute(r);
            callback.onCallback(r);
        }
    }
}
