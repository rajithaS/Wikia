package com.rajithas.apps.wikia.model;

/**
 * Created by rajitha.siriwardena on 15-Feb-15.
 */
public class WikiListItem implements IListItem {
    private int id;
    private String thumbnailUrl;
    private String title;
    private String url;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
