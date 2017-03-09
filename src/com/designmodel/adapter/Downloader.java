package com.designmodel.adapter;

import java.util.Map;

/**
 * Created by lisheng on 17-3-9.
 */
public class Downloader {

    public void download(String name,String url,Map<String,String> map){
        System.out.println("download name is ="+name);
        System.out.println("download url is = "+url);
        System.out.println("header is ="+map);
    }
}
