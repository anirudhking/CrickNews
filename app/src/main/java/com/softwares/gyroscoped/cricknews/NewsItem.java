package com.softwares.gyroscoped.cricknews;

import android.graphics.Bitmap;

/**
 * Created by lanke on 7/26/2017.
 */

public class NewsItem
{

    private String title;
    private String author;
    private String date;
    private String description;
    private Bitmap thumbnail;
    private String url;

    public NewsItem(String title, String author, String date, String description) {
        this.title = title;
        this.author = author;
        this.date = date;
        this.description = description;
    }

    public NewsItem(String title, String author, String date, String description, Bitmap thumbnail) {
        this.title = title;
        this.author = author;
        this.date = date;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public NewsItem(String title, String author, String date, String description, Bitmap thumbnail,String url) {
        this.title = title;
        this.author = author;
        this.date = date;
        this.description = description;
        this.thumbnail = thumbnail;
        this.url = url;
    }


    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}
