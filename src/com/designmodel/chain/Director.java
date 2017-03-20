package com.designmodel.chain;

/**
 * Created by lisheng on 17-3-20.
 */
public class Director extends Handler {
    @Override
    public void handleRequest(int money) {
        if (money < 1000) {
            System.out.println("director process money self!");
        } else
            handler.handleRequest(money);
    }

    public void setNextObject(Handler handler) {
        this.handler = handler;
    }
}
