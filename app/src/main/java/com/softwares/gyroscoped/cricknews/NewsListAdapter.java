package com.softwares.gyroscoped.cricknews;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lanke on 7/26/2017.
 */

public class NewsListAdapter extends ArrayAdapter<NewsItem>
{


    public NewsListAdapter(Context context, ArrayList<NewsItem> news) {
        super(context,0,news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {

        View newsItemView = convertView;

        if(newsItemView == null)
        {
           newsItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_item,parent,false);
        }

        NewsItem news = getItem(position);

        TextView titleTextView = (TextView)newsItemView.findViewById(R.id.title_text_view);
        TextView subtitleTextView = (TextView)newsItemView.findViewById(R.id.subtitle_text_view);
        TextView descriptionTextView = (TextView)newsItemView.findViewById(R.id.description_text_view);
        ImageView thumbnailImageView = (ImageView)newsItemView.findViewById(R.id.news_thumbnail);


        titleTextView.setText(news.getTitle());

        if(news.getAuthor()!= "null"){
        subtitleTextView.setText(news.getAuthor());}
        else
        {
            subtitleTextView.setText("");
        }
        descriptionTextView.setText(news.getDescription());
        thumbnailImageView.setImageBitmap(news.getThumbnail());

        return newsItemView;
    }




}
