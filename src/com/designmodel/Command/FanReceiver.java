package com.designmodel.Command;

/**
 * Created by lisheng on 17-3-20.
 */
public class FanReceiver extends Receiver {
    @Override
    public void execute() {
        System.out.println("light receive command");
    }
}
