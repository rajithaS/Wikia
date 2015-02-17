package com.rajithas.apps.wikia.ui;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rajithas.apps.wikia.R;
import com.rajithas.apps.wikia.model.WikiListItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rajitha.siriwardena on 15-Feb-15.
 */
public class WikiListAdapter extends WikiaAdapter<WikiListItem> {
    private final Context context;
    private final List<WikiListItem> data;
    private int totalServerDataCount = 0;

    public WikiListAdapter(Context context, List<WikiListItem> data, int layoutResource) {
        super(context, data, layoutResource);
        this.context = context;
        this.data = data;
    }

    public void appendItems(List<WikiListItem> data) {
        this.data.addAll(data);
    }

    public void removeItems() {
        data.clear();
    }

    public void setTotalServerDataCount(int totalServerDataCount) {
        this.totalServerDataCount = totalServerDataCount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        if (getItemViewType(position) == VIEW_TYPE_LOADING) {
            return view;
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.imgThumbnail = (ImageView) view.findViewById(R.id.imgThumbnail);
            holder.txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            holder.txtUrl = (TextView) view.findViewById(R.id.txtUrl);
            view.setTag(holder);
        }

        WikiListItem item = getItem(position);
        holder.txtTitle.setText(item.getTitle());
        holder.txtUrl.setText(item.getUrl());

        String thumbnailUrl = item.getThumbnailUrl();
        if (!TextUtils.isEmpty(thumbnailUrl)) {
            Picasso.with(context).load(thumbnailUrl)
                    .resize(48, 48)
                    .centerCrop().into(holder.imgThumbnail);
        } else {
            Picasso.with(context).load(R.drawable.no_image)
                    .resize(48, 48)
                    .centerCrop().into(holder.imgThumbnail);
        }

        return view;
    }

    @Override
    public View getLoadingView(int position, View convertView, ViewGroup parent) {
        if (position >= totalServerDataCount && totalServerDataCount > 0) {
            TextView txtNoMoreData = new TextView(context);
            txtNoMoreData.setTextColor(Color.BLACK);
            txtNoMoreData.setText(context.getString(R.string.no_more_data));
            txtNoMoreData.setGravity(Gravity.CENTER);
            txtNoMoreData.setPadding(0, 10, 0, 10);
            return txtNoMoreData;
        }

        View view = convertView;
        if (view == null || position == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.item_list_loading, parent, false);
        }

        return view;
    }

    class ViewHolder {
        ImageView imgThumbnail;
        TextView txtTitle;
        TextView txtUrl;
    }
}
