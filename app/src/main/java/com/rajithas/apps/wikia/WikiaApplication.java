package com.rajithas.apps.wikia;

import android.app.Application;

import com.rajithas.apps.wikia.dagger.AndroidDaggerModule;
import com.rajithas.apps.wikia.dagger.WikiaDaggerModule;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by rajitha.siriwardena on 15-Feb-15.
 */
public class WikiaApplication extends Application {
    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(getModules().toArray());
    }

    protected List<Object> getModules() {
        return Arrays.asList(new AndroidDaggerModule(this),
                new WikiaDaggerModule());
    }

    public void inject(Object object) {
        objectGraph.inject(object);
    }
}
