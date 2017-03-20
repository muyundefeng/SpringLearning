package com.designmodel.chain;

/**
 * Created by lisheng on 17-3-20.
 */
public class Chairman extends Handler {
    @Override
    public void handleRequest(int money) {
        if(money<5000)
            System.out.println("Chairman process money self");
    }

    @Override
    public void setNextObject(Handler handler) {

    }
}
