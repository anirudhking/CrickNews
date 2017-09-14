package com.softwares.gyroscoped.cricknews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by lanke on 7/27/2017.
 */

public class Utils
{

    private static String sampleJsonResponse ="{\"status\":\"ok\",\"source\":\"engadget\",\"sortBy\":\"top\",\"articles\":[{\"author\":\"Kris Naudus\",\"title\":\"The iPhone X vs. the competition: Beautiful screens and more\",\"description\":\"How does the iPhone X size up compared to the competition?\",\"url\":\"https://www.engadget.com/2017/09/12/iphone-x-vs-the-competition/\",\"urlToImage\":\"https://o.aolcdn.com/images/dims?resize=1200%2C630&crop=1200%2C630%2C0%2C0&quality=80&image_uri=https%3A%2F%2Fimg.vidible.tv%2Fprod%2F2017-09%2F12%2F59b8682d1de5a122ebeec35a%2F59b868e92a30cb20a3162d88_o_U_v1.jpg&client=cbc79c14efcebee57402&signature=98bbd6c326f3448cc2d36ea48187e9bdfb0d666b\",\"publishedAt\":\"2017-09-12T19:55:00Z\"},{\"author\":\"Chris Velazco\",\"title\":\"iPhone 8 and 8 Plus hands-on: Familiar, but not too familiar\",\"description\":\"Apple's iPhone 8 and 8 Plus are for those who fear change.\",\"url\":\"https://www.engadget.com/2017/09/12/iphone-8-and-8-plus-hands-on/\",\"urlToImage\":\"https://o.aolcdn.com/images/dims?thumbnail=1200%2C630&quality=80&image_uri=https%3A%2F%2Fo.aolcdn.com%2Fimages%2Fdims%3Fresize%3D2000%252C2000%252Cshrink%26image_uri%3Dhttp%253A%252F%252Fo.aolcdn.com%252Fhss%252Fstorage%252Fmidas%252F8e63a73e85223ca0de6eff97abfacd5%252F205660008%252F_MG_2788.jpg%26client%3Da1acac3e1b3290917d92%26signature%3D82aa9299b03c839d421a125f01e44219c60bfcef&client=cbc79c14efcebee57402&signature=ee09dbc715b49f52a9e8915042cb61d544b46a96\",\"publishedAt\":\"2017-09-12T20:17:00Z\"},{\"author\":\"Nathan Ingraham\",\"title\":\"Watch the iPhone 8 event in 13 minutes\",\"description\":\"Check out the new Apple Watch, Apple TV, iPhone 8, iPhone X and get on with your day.\",\"url\":\"https://www.engadget.com/2017/09/12/apple-iphone-8-event-in-13-minutes/\",\"urlToImage\":\"https://o.aolcdn.com/images/dims?thumbnail=1200%2C630&quality=80&image_uri=https%3A%2F%2Fo.aolcdn.com%2Fimages%2Fdims%3Fresize%3D2000%252C2000%252Cshrink%26image_uri%3Dhttp%253A%252F%252Fo.aolcdn.com%252Fhss%252Fstorage%252Fmidas%252F6832e0c73646eeafabae04caa7153b2e%252F205659717%252F20170912_120215.jpg%26client%3Da1acac3e1b3290917d92%26signature%3Dd13cdbce105f419a271621ac70c0b4e6e8cfe027&client=cbc79c14efcebee57402&signature=d49b6f50ebf24a799533b6c143d102c8d1521cce\",\"publishedAt\":\"2017-09-12T22:32:00Z\"},{\"author\":\"Dana Wollman\",\"title\":\"The most important stuff from today's iPhone event\",\"description\":\"Our first (but certainly not last) take on today's Apple news. Here's what mattered most.\",\"url\":\"https://www.engadget.com/2017/09/12/iphone-x-event-first-take/\",\"urlToImage\":\"https://o.aolcdn.com/images/dims?thumbnail=1200%2C630&quality=80&image_uri=https%3A%2F%2Fs.aolcdn.com%2Fhss%2Fstorage%2Fmidas%2Fc739fd66d514aa81250acc79281ceb2f%2F205660702%2Fiphoneevent.jpg&client=cbc79c14efcebee57402&signature=ccd5da17849d18d4cde84017e77fba80f8832a6c\",\"publishedAt\":\"2017-09-12T23:50:00Z\"},{\"author\":\"Chris Velazco\",\"title\":\"Apple iPhone X hands-on: 10 years in, Apple rewrites its rulebook\",\"description\":\"Our first photos of Apple's new iPhone X!\",\"url\":\"https://www.engadget.com/2017/09/12/apple-iphone-x-hands-on/\",\"urlToImage\":\"https://o.aolcdn.com/images/dims?thumbnail=1200%2C630&quality=80&image_uri=https%3A%2F%2Fo.aolcdn.com%2Fimages%2Fdims%3Fresize%3D2000%252C2000%252Cshrink%26image_uri%3Dhttp%253A%252F%252Fo.aolcdn.com%252Fhss%252Fstorage%252Fmidas%252Fba7eb80656e49339628d9a80133cd59b%252F205659722%252F20170912_120400.jpg%26client%3Da1acac3e1b3290917d92%26signature%3D693b0946308797b8fdf1e67595e5d77b0f44828f&client=cbc79c14efcebee57402&signature=ec5a5905a75d1d52e75a79fdcadc1fa44ffc8cc5\",\"publishedAt\":\"2017-09-12T19:26:00Z\"}]}";

    public static ArrayList<NewsItem> parseJsonResponse(String jsonResponse)
    {
        Bitmap newsImage = null;
        String author = "";
        String title = "";
        String description = "";
        String imageURL = "";
        String urlToArticle = "";

        ArrayList<NewsItem> news = new ArrayList<NewsItem>();
        if(jsonResponse.length() == 0)
            return null;

        try {
            JSONObject root = new JSONObject(jsonResponse);
            JSONArray articles = root.getJSONArray("articles");

            for(int i=0;i<articles.length();i++)
            {
                if(articles.getJSONObject(i).has("author")){
                 author = articles.getJSONObject(i).getString("author");
                }

                if(articles.getJSONObject(i).has("title")) {
                     title = articles.getJSONObject(i).getString("title");
                }

                if(articles.getJSONObject(i).has("description")) {
                     description = articles.getJSONObject(i).getString("description");
                }
                if(articles.getJSONObject(i).has("urlToImage")) {
                     imageURL = articles.getJSONObject(i).getString("urlToImage");
                }

                if(articles.getJSONObject(i).has("url")) {
                     urlToArticle = articles.getJSONObject(i).getString("url");
                }
                    try {
                URL url = new URL(imageURL);
                newsImage = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (Exception e) {
                    Log.e("Getting image for news","error getting image :"+e);
                }
                String publishedDate = articles.getJSONObject(i).getString("publishedAt");
                news.add(new NewsItem(title,author,publishedDate,description,newsImage,urlToArticle));
            }


        } catch (JSONException e) {
            Log.e("parseJsonResponse","Error parsing json "+e.toString());
        }

        return  news;
    }


    public static ArrayList<NewsItem> makeHttpConnection(String urlString)
    {
        ArrayList news = null;

         URL url = stringToUrl(urlString);

        try {
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.connect();

            if(connection.getResponseCode() == 200)
            {
                //connection successful
                InputStream inputStream = connection.getInputStream();
                String jsonRes = readFromStream(inputStream);
                news = parseJsonResponse(jsonRes);
            }
            else
            {
                Log.e("makeHttpConnection","connection problem error code"+connection.getResponseCode());

            }


        } catch (IOException e) {
            Log.e("makeHttpConnection","Error while connection "+e.toString());
        }




         return news;
    }


    public static ArrayList<NewsItem> parseJson()
    {
        ArrayList news = null;
        news = parseJsonResponse(sampleJsonResponse);

        return news;
    }

    private static URL stringToUrl(String urlString)
    {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (Exception e) {
            Log.e("stringToUrl","error while making url "+e);
        }
        return url;
    }

    private static String readFromStream(InputStream inputStream)
    {
        StringBuilder output = new StringBuilder("");


        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(inputStream,"UTF-8");
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line = bufferedReader.readLine();
            while(line != null)
            {
                output.append(line);
                line = bufferedReader.readLine();
            }

        } catch (Exception e) {
            Log.e("readFromStream","error while reading from input stream "+e.toString());
        }


        return output.toString();
        }


}
