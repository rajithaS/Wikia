package com.rajithas.apps.wikia.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.rajithas.apps.wikia.R;
import com.rajithas.apps.wikia.model.WikiListItem;

import java.util.List;

public class WikiListFragment extends ListFragment {
    public static final String TAG = WikiListFragment.class.getSimpleName();

    private OnWikiListLoadMoreListener loadMoreListener;
    private int totalServerItemCount;

    public static WikiListFragment newInstance() {
        WikiListFragment fragment = new WikiListFragment();
        return fragment;
    }

    public WikiListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wiki, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView listView = getListView();
        listView.setDivider(new ColorDrawable(Color.TRANSPARENT));
        listView.setDividerHeight(20);
        listView.setOnScrollListener(new InfiniteScollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (loadMoreListener != null) {
                    loadMoreListener.onLoadMore(page, totalItemsCount);
                }
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            loadMoreListener = (OnWikiListLoadMoreListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnWikiListLoadMoreListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        loadMoreListener = null;
    }

    public void setTotalServerItemCount(int totalServerItemCount) {
        this.totalServerItemCount = totalServerItemCount;
    }

    public void setListData(List<WikiListItem> result) {
        ListAdapter adapter = getListAdapter();
        if (adapter == null) {
            if (result.size() == 0) {
                setEmptyText(getActivity().getString(R.string.no_data_available));
            }

            adapter = new WikiListAdapter(getActivity(), result, R.layout.item_wiki_list);
            ((WikiListAdapter) adapter).setTotalServerDataCount(totalServerItemCount);
            setListAdapter(adapter);
        } else {
            ((WikiListAdapter) adapter).appendItems(result);
            ((WikiListAdapter) adapter).notifyDataSetChanged();
        }
    }

    public void setEmptyText(CharSequence emptyText) {
        View emptyView = getListView().getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    public interface OnWikiListLoadMoreListener {
        public void onLoadMore(int page, int totalItemsCount);
    }
}
