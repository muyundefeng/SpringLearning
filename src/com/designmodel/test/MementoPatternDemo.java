package com.designmodel.test;

import com.designmodel.memento.*;

/**
 * Created by lisheng on 17-3-27.
 */
public class MementoPatternDemo {
    public static void main(String[] args) {
        Originator originator = new Originator();
        CareTaker careTaker = new CareTaker();
        originator.setState("State #1");
        originator.setState("State #2");
        careTaker.add(originator.saveStateToMemento());//存放状态为2的元素
        originator.setState("State #3");
        careTaker.add(originator.saveStateToMemento());//存放状态为3的元素
        originator.setState("State #4");

        System.out.println("Current State: " + originator.getState());
        originator.getStateFromMemento(careTaker.get(0));
        System.out.println("First saved State: " + originator.getState());
        originator.getStateFromMemento(careTaker.get(1));
        System.out.println("Second saved State: " + originator.getState());

        System.out.println("-------------------------");

        Game game = new Game("FIRST","2017-10-23 9:00:00");
        GameMemoesList gameMemoesList = new GameMemoesList();
        gameMemoesList.addGameMemo(game.saveTomemo(game));
        Game game1 = new Game("second","2017-10-23 10:00:00");
        gameMemoesList.addGameMemo(game1.saveTomemo(game));

        System.out.println(gameMemoesList.getGameMemo(0).game.date);//得到第一存档时间
    }
}
