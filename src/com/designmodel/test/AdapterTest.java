package com.designmodel.test;

import com.designmodel.adapter.DownloaderAdapter;

/**
 * Created by lisheng on 17-3-9.
 */
public class AdapterTest {

    public static void main(String[] args) {
        DownloaderAdapter downloaderAdapter = new DownloaderAdapter();
        downloaderAdapter.download("hello","hello");
    }
}
