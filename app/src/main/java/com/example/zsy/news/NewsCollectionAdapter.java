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
 * Created by ZSY on 2017/7/10.
 */

public class NewsCollectionAdapter extends BaseAdapter {

    private List<NewsCollection> mList;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;


    public NewsCollectionAdapter(Context context, List<NewsCollection> date){
        mList=date;
        mInflater=LayoutInflater.from(context);
        mImageLoader=new ImageLoader();

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView=mInflater.inflate(R.layout.collect_item,null);
            viewHolder.imageView=(ImageView) convertView.findViewById(R.id.collect_image);
            viewHolder.textView=(TextView) convertView.findViewById(R.id.collect_title);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
        String url = mList.get(position).getImages();
        viewHolder.imageView.setTag(url);
        new ImageLoader().showImageByThread(viewHolder.imageView,url);
        viewHolder.textView.setText(mList.get(position).getTitle());
        return convertView;
    }
    class ViewHolder{
        public TextView textView;
        public ImageView imageView;
    }
}
//viewHolder.ivIcon.setImageResource(R.mipmap.ic_launcher);
//        String url= mList.get(position).newsIconUrl;
//        viewHolder.ivIcon.setTag(url);
//        new ImageLoader().showImageByThread(viewHolder.ivIcon,url);
////        mImageLoader.showImageByAsyncTask(viewHolder.ivIcon,url);
//        viewHolder.tvTitle.setText(mList.get(position).newsTitle);