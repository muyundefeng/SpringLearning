package com.designmodel.mediator;

/**
 * Created by lisheng on 17-3-27.
 */
public class User {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String name){
        this.name  = name;
    }
    //调用中介者，对象之间的信息共享通过中介者实现，协调对象之间的耦合关系
    public void sendMessage(String message){
        ChatRoom.showMessage(this,message);
    }
}
