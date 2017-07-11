package com.example.zsy.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;


import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ZSY on 2017/5/14.
 */

public class ContentActivity extends AppCompatActivity {

    public String URL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);
        Button share = (Button) findViewById(R.id.share_url);
        Button collect = (Button) findViewById(R.id.collecet_url);
        WebView webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);

        final Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        final String url = "http://daily.zhihu.com/story/" + id;
        URL = "http://news-at.zhihu.com/api/4/news/"+id;



        webView.loadUrl(url);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent textIntent = new Intent(Intent.ACTION_SEND);
                textIntent.setType("text/plain");
                textIntent.putExtra(Intent.EXTRA_TEXT, url);
                startActivity(Intent.createChooser(textIntent, "分享"));
            }
        });
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestWithOKHttp();
                Toast.makeText(ContentActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                List<NewsCollection> newsCollections = DataSupport.findAll(NewsCollection.class);
                for(NewsCollection newsCollection:newsCollections){
                    Log.d("ContentActivity",newsCollection.getImages());
                    Log.d("ContentActivity",newsCollection.getShare_url());
                    Log.d("ContentActivity",newsCollection.getTitle());
                }
            }
        });
    }
    private void parseJSONWithJSONObject(String jsonData){
        try {
            JSONObject jsonObject =new JSONObject(jsonData);
            NewsCollection newsCollection = new NewsCollection();
            newsCollection.setTitle(jsonObject.getString("title"));
            newsCollection.setShare_url(jsonObject.getString("share_url"));
            JSONArray jsonArray =jsonObject.getJSONArray("images");
            for (int i=0;i<jsonArray.length();i++){
                newsCollection.setImages(jsonArray.getString(i));
            }
            newsCollection.save();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendRequestWithOKHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request =new Request.Builder().url(URL).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}


