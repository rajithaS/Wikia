package com.rajithas.apps.wikia.task;

import com.rajithas.apps.wikia.model.WikiIdResult;
import com.rajithas.apps.wikia.network.HttpApi;
import com.rajithas.apps.wikia.network.IController;
import com.rajithas.apps.wikia.network.NetworkException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by rajitha.siriwardena on 15-Feb-15.
 */
public class WikiIdRetrievalTask implements ITask<WikiIdResult, IController> {

    private final HttpApi httpApi;

    @Inject
    public WikiIdRetrievalTask(@Named("HttpApi") HttpApi httpApi) {
        this.httpApi = httpApi;
    }

    @Override
    public WikiIdResult execute(IController input) throws TaskExecutionException {
        if (input == null) throw new IllegalArgumentException("Controller is null");
        try {
            WikiIdResult result = new WikiIdResult();

            String httpResult = httpApi.getContent(input);
            JSONObject jsonObject = new JSONObject(httpResult);

            if (jsonObject.has("items")) {
                result.setIds(getIds(jsonObject));
            }

            if (jsonObject.has("next")) {
                result.setNext(jsonObject.getInt("next"));
            }

            if (jsonObject.has("total")) {
                result.setTotal(jsonObject.getInt("total"));
            }

            if (jsonObject.has("batches")) {
                result.setBatches(jsonObject.getInt("batches"));
            }

            if (jsonObject.has("currentBatch")) {
                result.setCurrentBatch(jsonObject.getInt("currentBatch"));
            }

            return result;
        } catch (NetworkException | JSONException e) {
            e.printStackTrace();
            throw new TaskExecutionException(e);
        }
    }

    private List<String> getIds(JSONObject jsonObject) throws JSONException {
        List<String> idList = new ArrayList<>();
        JSONArray items = jsonObject.getJSONArray("items");

        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            idList.add(item.get("id").toString());
        }

        return idList;
    }
}
