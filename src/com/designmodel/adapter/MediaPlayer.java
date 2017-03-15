package com.designmodel.adapter;

/**目标接口，就是要把需要适配的类转化成该接口形式
 * Created by lisheng on 17-3-15.
 */
public interface MediaPlayer {
    public void play(String audioType, String fileName);
}
