package com.rajithas.apps.wikia.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rajitha.siriwardena on 16-Feb-15.
 */
public class WikiIdResult implements Parcelable {
    private List<String> ids = new ArrayList<>();
    private int next;
    private int total;
    private int batches;
    private int currentBatch;

    public static Creator<WikiIdResult> CREATOR = new Creator<WikiIdResult>() {
        @Override
        public WikiIdResult createFromParcel(Parcel parcel) {
            return new WikiIdResult(parcel);
        }

        @Override
        public WikiIdResult[] newArray(int i) {
            return new WikiIdResult[i];
        }
    };

    public WikiIdResult() {
    }

    public WikiIdResult(Parcel p) {
        p.readStringList(ids);
        next = p.readInt();
        total = p.readInt();
        batches = p.readInt();
        currentBatch = p.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(ids);
        dest.writeInt(next);
        dest.writeInt(total);
        dest.writeInt(batches);
        dest.writeInt(currentBatch);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getBatches() {
        return batches;
    }

    public void setBatches(int batches) {
        this.batches = batches;
    }

    public int getCurrentBatch() {
        return currentBatch;
    }

    public void setCurrentBatch(int currentBatch) {
        this.currentBatch = currentBatch;
    }
}
