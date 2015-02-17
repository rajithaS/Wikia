package com.rajithas.apps.wikia.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.rajithas.apps.wikia.WikiaApplication;

/**
 * Created by rajitha.siriwardena on 15-Feb-15.
 */
public class WikiaActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((WikiaApplication) getApplication()).inject(this);
    }
}
