package com.rajithas.apps.wikia.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.rajithas.apps.wikia.model.IListItem;

import java.util.List;

/**
 * Created by rajitha.siriwardena on 15-Feb-15.
 */
public abstract class WikiaAdapter<T extends IListItem> extends BaseAdapter {
    public static final int VIEW_TYPE_ITEM = 0;
    public static final int VIEW_TYPE_LOADING = 1;

    private final List<T> data;
    private final int layoutResource;
    private final LayoutInflater layoutInflater;

    public WikiaAdapter(Context context, List<T> data, int layoutResource) {
        this.data = data;
        this.layoutResource = layoutResource;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public boolean isEnabled(int position) {
        return getItemViewType(position) == VIEW_TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position >= data.size() ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getCount() {
        return data.size() + 1;
    }

    @Override
    public T getItem(int position) {
        return getItemViewType(position) == VIEW_TYPE_ITEM ? data.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return getItemViewType(position) == VIEW_TYPE_ITEM ? data.get(position).getId() : -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == VIEW_TYPE_LOADING) {
            return getLoadingView(position, convertView, parent);
        }

        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(layoutResource, parent, false);
        }
        return view;
    }

    public abstract View getLoadingView(int position, View convertView, ViewGroup parent);
}
