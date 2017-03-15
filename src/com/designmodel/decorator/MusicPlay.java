package com.designmodel.decorator;

/**
 * Created by lisheng on 17-3-15.
 */
public class MusicPlay extends PhoneDecorator {

    public MusicPlay(Phone phone) {
        super(phone);
    }

    @Override
    public void callSomeone() {
        super.callSomeone();
        this.playMusic();
    }

    //为手机增加音乐播放的功能
    public void playMusic(){
        System.out.println("this phone can play music");
    }
}
