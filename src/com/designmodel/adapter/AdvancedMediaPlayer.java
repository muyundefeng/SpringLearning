package com.designmodel.adapter;

/**适配者抽象类
 * Created by lisheng on 17-3-15.
 */
public interface AdvancedMediaPlayer {
    public void playVlc(String fileName);
    public void playMp4(String fileName);
}
