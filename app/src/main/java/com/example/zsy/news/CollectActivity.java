package com.example.zsy.news;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZSY on 2017/7/10.
 */

public class CollectActivity extends AppCompatActivity {

    private ListView mListView;
    private List<NewsCollection> newsCollectionList = new ArrayList<>();
    List<NewsCollection> newsCollections = DataSupport.findAll(NewsCollection.class);


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collect);
        initNews();
        mListView=(ListView) findViewById(R.id.collection);
        NewsCollectionAdapter newsCollectionAdapter =new NewsCollectionAdapter(CollectActivity.this,newsCollectionList);
        mListView.setAdapter(newsCollectionAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsCollection newsCollection = newsCollectionList.get(position);
                Intent intent = new Intent(CollectActivity.this,WebCollectionActivity.class);
                intent.putExtra("url",newsCollection.getShare_url());
                startActivity(intent);
            }
        });
    }
    private void initNews(){
        for (NewsCollection newsCollection:newsCollections){
            newsCollectionList.add(newsCollection);
        }
    }
}
