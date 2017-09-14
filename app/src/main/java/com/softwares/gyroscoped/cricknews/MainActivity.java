package com.softwares.gyroscoped.cricknews;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<NewsItem>>{

    private static String WEB_KEY = "url";
    ProgressBar progressBar = null;
    TextView emptyView = null;
    private String url = "https://newsapi.org/v1/articles?source=espn-cric-info&sortBy=latest&apiKey=2d77b12d7d18444485337680b86e7c3f";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }



    protected void init()
    {
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        emptyView = (TextView)findViewById(R.id.empty_text_view);
        emptyView.setVisibility(View.GONE);


        if(!isNetworkConnected())
        {
            progressBar.setVisibility(View.GONE);
            emptyView.setText("No Network Connection");
            emptyView.setVisibility(View.VISIBLE);
        }

        else {
            getSupportLoaderManager().initLoader(1, null, this);
            AdView adView = (AdView) findViewById(R.id.adView);
            AdRequest request = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
           // AdRequest request = new AdRequest.Builder().build();
            adView.loadAd(request);
        }
    }

    @Override
    public Loader<ArrayList<NewsItem>> onCreateLoader(int id, Bundle args) {
        NewsLoader newsLoader = new NewsLoader(getBaseContext(),url);
        return newsLoader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<NewsItem>> loader, final ArrayList<NewsItem> data) {

        progressBar.setVisibility(View.GONE);
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setEmptyView(emptyView);

        if(data != null) {
            NewsListAdapter newsListAdapter = new NewsListAdapter(this, data);
            listView.setAdapter(newsListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsItem newsItem = (NewsItem)data.get(position);
                String linkOfArticle = newsItem.getUrl();

                Intent intentToWebActivity = new Intent(getBaseContext(),NewsWebsiteActivity.class);
                intentToWebActivity.putExtra(WEB_KEY,linkOfArticle);
                intentToWebActivity.putExtra("TITLE",newsItem.getTitle());
                startActivity(intentToWebActivity);
            }
        });

        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<NewsItem>> loader) {

    }


    private boolean isNetworkConnected()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = (NetworkInfo)connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


}
