package com.softwares.gyroscoped.cricknews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class NewsWebsiteActivity extends AppCompatActivity {

   private URI articleUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_website);

       init();


    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.share_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = articleUrl.toString()+" Shared using : Crick news "+getResources().getString(R.string.app_url);
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Shared using : Crick news");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));


        return true;
    }

    private void init()
    {

        Intent intentFromMainActivity = getIntent();
        String url = intentFromMainActivity.getStringExtra("url");
        WebView webView = (WebView)findViewById(R.id.web_view);

        try {
             articleUrl = new URI(url);
        } catch (Exception e) {
            e.printStackTrace();
            url = null;
        }

        if(articleUrl != null)
        webView.loadUrl(url);

        setAdmob();

    }


    private void setAdmob()
    {
        AdView adView = (AdView)findViewById(R.id.adView);
      //  AdRequest request = new AdRequest.Builder().build();
        AdRequest request = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(request);
    }
}
