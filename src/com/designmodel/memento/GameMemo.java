package com.designmodel.memento;

/**备忘录模式，模拟游戏存档
 * Created by lisheng on 17-3-27.
 */
public class GameMemo {

    public String stage;//保存游戏的阶段

    public Game game;

    public GameMemo(String stage,Game game){
        this.stage = stage;
        this.game = game;
    }
}
