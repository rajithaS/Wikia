package com.rajithas.apps.wikia.network;

import com.rajithas.apps.wikia.Config;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by rajitha.siriwardena on 15-Feb-15.
 */
public class WikiaController implements IController {
    private final String controller = "WikisApi";
    private String method;
    private List<NameValuePair> parameters = new LinkedList<>();

    @Inject
    public WikiaController() {
        parameters.add(new BasicNameValuePair("controller", controller));
    }

    public void addParameter(String parameter, String value) {
        parameters.add(new BasicNameValuePair(parameter, value));
    }

    public void setMethod(String method) {
        this.method = method;
        parameters.add(new BasicNameValuePair("method", method));
    }

    @Override
    public String getURL() {
        if (method == null) throw new IllegalArgumentException("method is not set");
        String url = Config.SERVER_URL;
        if (!url.endsWith("?")) {
            url += "?";
        }

        url += URLEncodedUtils.format(parameters, "utf-8");

        return url;
    }
}
