package com.example.zsy.news;

import org.litepal.crud.DataSupport;

/**
 * Created by ZSY on 2017/7/9.
 */

public class NewsCollection extends DataSupport{
    private String title;
    private String images;
    private String share_url;

    public void setTitle(String title){
        this.title=title;
    }
    public void setImages(String images){
        this.images=images;
    }
    public void setShare_url(String share_url){
        this.share_url=share_url;
    }

    public String getTitle(){
        return title;
    }
    public String getImages(){
        return images;
    }
    public String getShare_url(){
        return share_url;
    }
}
