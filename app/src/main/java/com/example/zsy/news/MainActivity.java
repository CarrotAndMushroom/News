package com.example.zsy.news;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mListview;
    private static String URL="http://news-at.zhihu.com/api/4/news/latest";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about_app:
                Toast.makeText(this, "这是一个简单而又难看的新闻app，原谅我目前只能开发成这样。", Toast.LENGTH_SHORT).show();
                break;
            case R.id.collect:
                Intent intent =new Intent(MainActivity.this,CollectActivity.class);
                startActivity(intent);
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListview=(ListView) findViewById(R.id.lv_main);
        new NewsAsyncTask().execute(URL);
    }
    private List<NewsBean> getJsonData(String url){
        List<NewsBean> newsBeanList= new ArrayList<>();
        try {
            String jsonString = readSteam(new URL(url).openStream());
            JSONObject jsonObject;
            NewsBean newsBean;
            try {
                jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray0 = jsonObject.getJSONArray("stories");
                JSONArray jsonArray2 = jsonObject.getJSONArray("top_stories");
                for (int i=0;i<jsonArray0.length();i++) {
                    jsonObject = jsonArray0.getJSONObject(i);
                    newsBean = new NewsBean();
                    JSONArray jsonArray1 = jsonObject.getJSONArray("images");
                    for (int m = 0; m < jsonArray1.length(); m++) {
                        newsBean.newsIconUrl = jsonArray1.getString(m);
                    }
                    newsBean.newsTitle = jsonObject.getString("title");
                    newsBean.storiesId = jsonObject.getString("id");
                    newsBeanList.add(newsBean);
                }
//                for (int n=0;n<jsonArray2.length();n++){
//                    jsonObject = jsonArray2.getJSONObject(n);
//                    newsBean=new NewsBean();
//                    newsBean.newsIconUrl=jsonObject.getString("image");
//                    newsBean.newsTitle=jsonObject.getString("title");
//                    newsBean.topStoryId=jsonObject.getString("id");
//                    newsBeanList.add(newsBean);
//                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newsBeanList;

    }
    private String readSteam(InputStream is){
        InputStreamReader isr;
        String result="";
        try {
            String line= "";
            isr=new InputStreamReader(is,"utf-8");
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine())!=null){
                    result+=line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    class NewsAsyncTask extends AsyncTask<String,Void,List<NewsBean>>{
        @Override
        protected List<NewsBean> doInBackground(String... params) {
            return getJsonData(params[0]);
        }

        @Override
        protected void onPostExecute(final List<NewsBean> newsBeans) {
            super.onPostExecute(newsBeans);
            NewsAdapter adapter = new NewsAdapter(MainActivity.this,newsBeans);
            mListview.setAdapter(adapter);
            mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    NewsBean newsbean =newsBeans.get(position);
                    Intent intent = new Intent(MainActivity.this,ContentActivity.class);
                    intent.putExtra("id",newsbean.storiesId);
                    startActivity(intent);
                 }
            });
        }
    }
}
