package com.designmodel.test;

import com.designmodel.template.Chrome;
import com.designmodel.template.CustomDownloader;
import com.designmodel.template.Donwloader;
import com.designmodel.template.XunleiDownloader;

/**
 * Created by lisheng on 17-3-27.
 */
public class TemplateTest {
    public static void main(String[] args) {
        Donwloader donwloader = new Chrome();
        donwloader.templateMethod();
        Donwloader donwloader1 = new CustomDownloader();
        donwloader1.templateMethod();
        Donwloader donwloader2 = new XunleiDownloader();
        donwloader2.templateMethod();
    }
}
