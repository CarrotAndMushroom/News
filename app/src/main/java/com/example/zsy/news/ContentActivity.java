package com.example.zsy.news;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

/**
 * Created by ZSY on 2017/5/14.
 */

public class ContentActivity extends AppCompatActivity{



    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);
        Button share=(Button) findViewById(R.id.share_url);
        Button collect =(Button) findViewById(R.id.collecet_url);
        WebView webView =(WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        final Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        final String url = "http://daily.zhihu.com/story/"+id;

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

            }
        });

    }


}
