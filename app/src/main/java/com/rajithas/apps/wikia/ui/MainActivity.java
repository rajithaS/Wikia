package com.rajithas.apps.wikia.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.rajithas.apps.wikia.R;
import com.rajithas.apps.wikia.model.WikiIdResult;
import com.rajithas.apps.wikia.model.WikiListItem;
import com.rajithas.apps.wikia.network.IController;
import com.rajithas.apps.wikia.network.Network;
import com.rajithas.apps.wikia.network.WikiaController;
import com.rajithas.apps.wikia.task.ITask;
import com.rajithas.apps.wikia.util.ICallback;
import com.rajithas.apps.wikia.util.ILoader;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;


public class MainActivity extends WikiaActivity implements WikiListFragment.OnWikiListLoadMoreListener {
    private static final String STATE_WIKI_RESULT = "wikiresult";

    @Inject ILoader loader;
    @Inject @Named("WikiIDListRetrievalTask") ITask<WikiIdResult, IController> wikiIdListTask;
    @Inject @Named("WikiDetailRetrievalTask") ITask<List<WikiListItem>, List<String>> wikiDetailTask;
    @Inject Context applicationContext;

    private WikiIdResult wikiIdListResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragmentContainer, WikiListFragment.newInstance(),
                    WikiListFragment.TAG).commit();

            loadListItems(1);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STATE_WIKI_RESULT, wikiIdListResult);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        wikiIdListResult = savedInstanceState.getParcelable(STATE_WIKI_RESULT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            refreshList();
        }
        return super.onOptionsItemSelected(item);
    }

    public WikiListFragment getWikiListFragment() {
        getSupportFragmentManager().executePendingTransactions();
        return (WikiListFragment) getSupportFragmentManager().findFragmentByTag(WikiListFragment.TAG);
    }

    private void refreshList() {
        WikiListAdapter listAdapter = (WikiListAdapter) getWikiListFragment().getListAdapter();
        if (listAdapter != null) {
            listAdapter.removeItems();
            listAdapter.notifyDataSetChanged();
        }
        loadListItems(1);
    }

    public void loadListItems(final int batch) {
        WikiaController controller = new WikiaController();
        controller.setMethod("getList");
        controller.addParameter("hub", "Gaming");
        controller.addParameter("lang", "en");
        controller.addParameter("limit", "25");
        controller.addParameter("batch", String.valueOf(batch));

        final WikiListFragment wikiListFragment = getWikiListFragment();

        loader.load(wikiIdListTask, new ICallback<WikiIdResult>() {
            @Override
            public void onCallback(WikiIdResult result) {
                if (result != null && result.getIds().size() > 0) {
                    wikiIdListResult = result;

                    wikiListFragment.setTotalServerItemCount(result.getTotal());

                    loader.load(wikiDetailTask, new ICallback<List<WikiListItem>>() {
                        @Override
                        public void onCallback(List<WikiListItem> result) {
                            if (result != null) {
                                wikiListFragment.setListData(result);
                            } else {
                                wikiListFragment.setEmptyText(getString(R.string.no_data_available));
                            }
                        }
                    }, result.getIds());

                } else {
                    if (Network.isNetworkAvailable(applicationContext)) {
                        wikiListFragment.setEmptyText(getString(R.string.couldnt_load_data));
                    } else {
                        wikiListFragment.setEmptyText(getString(R.string.not_connected_to_internet));
                    }
                }
            }
        }, controller);
    }

    @Override
    public void onLoadMore(int page, int totalItemsCount) {
        if (wikiIdListResult != null) {
            int batchToLoad = wikiIdListResult.getCurrentBatch() + 1;
            if (batchToLoad <= wikiIdListResult.getBatches()) {
                loadListItems(batchToLoad);
            }
        }
    }
}
