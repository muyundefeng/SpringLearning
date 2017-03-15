package com.designmodel.test;

import com.designmodel.composite.Component;
import com.designmodel.composite.impl.Folder;
import com.designmodel.composite.impl.Img;
import com.designmodel.composite.impl.TextFile;

/**
 * Created by lisheng on 17-3-15.
 */
public class CompositeTest {
    public static void main(String[] args) {
        Component text = new TextFile();
        Component Img = new Img();
        Component folder = new Folder();
        Component Subfolder = new Folder();
        folder.addComponent(text);
        folder.addComponent(Img);
        folder.addComponent(Subfolder);
        folder.killVirtus();

    }
}
