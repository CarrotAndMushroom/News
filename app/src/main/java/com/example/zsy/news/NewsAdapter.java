package com.example.zsy.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ZSY on 2017/5/10.
 */

public class NewsAdapter extends BaseAdapter {

    private List<NewsBean> mList;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;


    public NewsAdapter(Context context,List<NewsBean> data){
        mList=data;
        mInflater=LayoutInflater.from(context);
        mImageLoader= new ImageLoader();

    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView=mInflater.inflate(R.layout.news_item,null);
            viewHolder.ivIcon=(ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tvTitle=(TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.ivIcon.setImageResource(R.mipmap.ic_launcher);
        String url= mList.get(position).newsIconUrl;
        viewHolder.ivIcon.setTag(url);
        new ImageLoader().showImageByThread(viewHolder.ivIcon,url);
//        mImageLoader.showImageByAsyncTask(viewHolder.ivIcon,url);
        viewHolder.tvTitle.setText(mList.get(position).newsTitle);
        return convertView;
    }

    class ViewHolder{
        public TextView tvTitle;
        public ImageView ivIcon;

    }
}
