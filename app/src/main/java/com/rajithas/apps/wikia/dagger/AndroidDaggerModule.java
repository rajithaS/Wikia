package com.rajithas.apps.wikia.dagger;

import android.content.Context;

import com.rajithas.apps.wikia.WikiaApplication;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rajitha.siriwardena on 15-Feb-15.
 */
@Module(library = true)
public class AndroidDaggerModule {
    private final WikiaApplication application;

    public AndroidDaggerModule(WikiaApplication application) {
        this.application = application;
    }

    @Provides
    public Context getContext() {
        return application;
    }
}
