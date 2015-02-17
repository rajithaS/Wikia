package com.rajithas.apps.wikia.network;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import javax.inject.Inject;

/**
 * Created by rajitha.siriwardena on 15-Feb-15.
 */
public class HttpApi implements IApi<String, IController> {
    private static final String TAG = HttpApi.class.getSimpleName();

    @Inject
    public HttpApi() {
    }

    @Override
    public String getContent(IController controller) throws NetworkException {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(controller.getURL());
        try {
            HttpResponse response = client.execute(request);
            StatusLine status = response.getStatusLine();
            if (status.getStatusCode() != 200) {
                throw new NetworkException("Invalid response from server: " + status.toString());
            }
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            throw new NetworkException(e);
        }
    }
}
