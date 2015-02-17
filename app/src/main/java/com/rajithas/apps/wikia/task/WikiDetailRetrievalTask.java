package com.rajithas.apps.wikia.task;

import com.google.common.base.Joiner;
import com.rajithas.apps.wikia.model.IFactory;
import com.rajithas.apps.wikia.model.WikiListItem;
import com.rajithas.apps.wikia.network.HttpApi;
import com.rajithas.apps.wikia.network.NetworkException;
import com.rajithas.apps.wikia.network.WikiaController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by rajitha.siriwardena on 15-Feb-15.
 */
public class WikiDetailRetrievalTask implements ITask<List<WikiListItem>, List<String>> {
    private final HttpApi httpApi;
    private final IFactory<WikiListItem, JSONObject> factory;

    @Inject
    public WikiDetailRetrievalTask(@Named("HttpApi") HttpApi httpApi,
                                   @Named("WikiListItemFactory") IFactory<WikiListItem, JSONObject> factory) {
        this.httpApi = httpApi;
        this.factory = factory;
    }

    @Override
    public List<WikiListItem> execute(List<String> idList) throws TaskExecutionException {
        if (idList == null) throw new IllegalArgumentException("input is null");

        try {
            WikiaController controller = new WikiaController();
            controller.setMethod("getDetails");
            controller.addParameter("ids", Joiner.on(",").join(idList));
            // Should uncomment. Commented because resizing on the server doesn't seem to work properly.
            // controller.addParameter("height", "48");
            // controller.addParameter("width", "48");

            String result = httpApi.getContent(controller);
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.has("items")) {
                return createListItems(idList, jsonObject.getJSONObject("items"));
            }

        } catch (NetworkException | JSONException e) {
            e.printStackTrace();
            throw new TaskExecutionException(e);
        }
        return null;
    }

    private List<WikiListItem> createListItems(List<String> idList, JSONObject items) throws JSONException {
        List<WikiListItem> listItems = new ArrayList<>();

        for (String id : idList) {
            JSONObject item = items.getJSONObject(id);
            listItems.add(factory.create(item));
        }

        return listItems;
    }
}
