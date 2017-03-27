package com.designmodel.test;

import com.designmodel.mediator.User;

/**
 * Created by lisheng on 17-3-27.
 */
public class MediatorTest {
    public static void main(String[] args) {
        User robert = new User("Robert");
        User john = new User("John");

        robert.sendMessage("Hi! John!");
        john.sendMessage("Hello! Robert!");
    }
}
