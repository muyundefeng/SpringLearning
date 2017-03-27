package com.designmodel.memento;

/**
 * Created by lisheng on 17-3-27.
 */
public class Game {

    public String stage;

    public String date;

    public Game(String stage,String date){
        this.stage = stage;
        this.date = date;
    }
    //保存游戏存档，一个游戏存档对应于一个GameMemto对象。
    public GameMemo saveTomemo(Game game){
        return new GameMemo(game.stage,game);
    }
}
