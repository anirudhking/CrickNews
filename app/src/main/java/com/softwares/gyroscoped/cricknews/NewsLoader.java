package com.softwares.gyroscoped.cricknews;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import java.util.ArrayList;

/**
 * Created by lanke on 7/27/2017.
 */

public class NewsLoader extends AsyncTaskLoader<ArrayList<NewsItem>> {

    ArrayList<NewsItem> news;

    private String url;

    public NewsLoader(Context context,String url) {
        super(context);
        this.url = url;
        forceLoad();
    }


    @Override
    public ArrayList<NewsItem> loadInBackground() {
       // news = Utils.makeHttpConnection(url);
        news = Utils.parseJson();
        return news;
    }
}
