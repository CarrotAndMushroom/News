package com.example.zsy.news;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by ZSY on 2017/5/11.
 */

public class ImageLoader {
    private ImageView mImageView;
    private String mUrl;
    private LruCache<String,Bitmap> mCache;

    public ImageLoader(){

        int maxMemory=(int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory/4;
        mCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };

    }
    public void addBitmapToCache(String url,Bitmap bitmap){
        if (getBitmapFromURL(url)==null){
            mCache.put(url,bitmap);
        }
    }
    public Bitmap getBitmapToCache(String url){
        return mCache.get(url);
    }


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mImageView.getTag().equals(mUrl)){
                mImageView.setImageBitmap((Bitmap) msg.obj);
            }
        }
    };



    public void showImageByThread(ImageView imageView, final String url) {
        mUrl=url;
        mImageView= imageView;
        new Thread() {
            @Override
            public void run() {
                super.run();
                Bitmap bitmap=getBitmapFromURL(url);
                Message message = Message.obtain();
                message.obj=bitmap;
                mHandler.sendMessage(message);
            }
        }.start();
    }

    public Bitmap getBitmapFromURL(String urlString)  {
        Bitmap bitmap;
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            bitmap=BitmapFactory.decodeStream(is);
            connection.disconnect();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void showImageByAsyncTask(ImageView imageView,String url){
        Bitmap bitmap = getBitmapFromURL(url);
        if (bitmap==null){
            new NewsAsyncTask(imageView,url).execute(url);
        }else {
            imageView.setImageBitmap(bitmap);
        }


    }
    private class NewsAsyncTask extends AsyncTask<String,Void,Bitmap>{

        private ImageView mImageView;
        private String mUrl;
        public NewsAsyncTask(ImageView imageView,String url){
            mImageView=imageView;
            mUrl=url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url =params[0];
            Bitmap bitmap = getBitmapFromURL(params[0]);
            if (bitmap!=null){
                addBitmapToCache(url,bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (mImageView.getTag().equals(mUrl)){
                super.onPostExecute(bitmap);
            }

        }
    }
}
