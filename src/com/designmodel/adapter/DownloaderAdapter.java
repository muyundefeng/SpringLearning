package com.designmodel.adapter;

/**
 * Created by lisheng on 17-3-9.
 */
public class DownloaderAdapter {

    private Downloader downloader;

    public DownloaderAdapter(){
        downloader = new Downloader();
    }

    public void download(String name ,String url){
        downloader.download(name,url,null);
    }
}
