package com.designmodel.memento;

import java.util.ArrayList;
import java.util.List;

/**操作存档链表
 * Created by lisheng on 17-3-27.
 */
public class GameMemoesList {
    public List<GameMemo> gameMemoList = new ArrayList<>();

    //存档
    public void addGameMemo(GameMemo gameMemo){
        gameMemoList.add(gameMemo);
    }
    //恢复游戏存档
    public GameMemo getGameMemo(int index){
        return gameMemoList.get(index);
    }
}
