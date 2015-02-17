package com.rajithas.apps.wikia.dagger;

import com.rajithas.apps.wikia.model.IFactory;
import com.rajithas.apps.wikia.model.WikiIdResult;
import com.rajithas.apps.wikia.model.WikiListItem;
import com.rajithas.apps.wikia.model.WikiListItemFactory;
import com.rajithas.apps.wikia.network.HttpApi;
import com.rajithas.apps.wikia.network.IController;
import com.rajithas.apps.wikia.task.ITask;
import com.rajithas.apps.wikia.task.WikiDetailRetrievalTask;
import com.rajithas.apps.wikia.task.WikiIdRetrievalTask;
import com.rajithas.apps.wikia.ui.MainActivity;
import com.rajithas.apps.wikia.ui.WikiListFragment;
import com.rajithas.apps.wikia.util.AsyncLoader;
import com.rajithas.apps.wikia.util.ILoader;

import org.json.JSONObject;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rajitha.siriwardena on 15-Feb-15.
 */
@Module(library = true,
        includes = {
                AndroidDaggerModule.class
        },
        injects = {
                MainActivity.class,
                WikiListFragment.class
        })
public class WikiaDaggerModule {

    @Provides
    @Singleton
    @Named("HttpApi")
    public HttpApi getApi(HttpApi api) {
        return api;
    }

    @Provides
    public ILoader provideLoader(AsyncLoader asyncLoader) {
        return asyncLoader;
    }

    @Provides
    @Singleton
    @Named("WikiIDListRetrievalTask")
    public ITask<WikiIdResult, IController> getWikiIdRetrievalTask(WikiIdRetrievalTask wikiIdRetrievalTask) {
        return wikiIdRetrievalTask;
    }

    @Provides
    @Singleton
    @Named("WikiDetailRetrievalTask")
    public ITask<List<WikiListItem>, List<String>> getWikiDetailRetrievalTask(WikiDetailRetrievalTask wikiDetailRetrievalTask) {
        return wikiDetailRetrievalTask;
    }

    @Provides
    @Singleton
    @Named("WikiListItemFactory")
    public IFactory<WikiListItem, JSONObject> getWikiListItemFactory(WikiListItemFactory wikiListItemFactory) {
        return wikiListItemFactory;
    }
}
