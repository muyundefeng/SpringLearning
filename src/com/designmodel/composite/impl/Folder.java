package com.designmodel.composite.impl;

import com.designmodel.composite.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisheng on 17-3-15.
 */
public class Folder implements Component {

    public List<Component> componentList = new ArrayList<>();

    @Override
    public void addComponent(Component component) {
        if(component instanceof Folder){
            System.out.println("add folder to list");
        }
        if(component instanceof Img)
            System.out.println("add img to list");
        if(component instanceof TextFile)
            System.out.println("add text to list");
        componentList.add(component);
    }

    @Override
    public Component removeComponet() {
        return componentList.remove(componentList.size()-1);
    }

    @Override
    public void killVirtus() {
        for(Component component:componentList){
            component.killVirtus();
        }
    }
}
