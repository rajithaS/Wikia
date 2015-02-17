package com.rajithas.apps.wikia.model;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * Created by rajitha.siriwardena on 15-Feb-15.
 */
public class WikiListItemFactory implements IFactory<WikiListItem, JSONObject> {

    @Inject
    public WikiListItemFactory() {
    }

    @Override
    public WikiListItem create(JSONObject input) {
        if (input == null) throw new IllegalArgumentException("input is null");

        try {
            WikiListItem wikiListItem = new WikiListItem();
            wikiListItem.setId(input.getInt("id"));
            wikiListItem.setTitle(input.getString("title"));
            wikiListItem.setUrl(input.getString("url"));
            wikiListItem.setThumbnailUrl(input.getString("image"));

            return wikiListItem;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
