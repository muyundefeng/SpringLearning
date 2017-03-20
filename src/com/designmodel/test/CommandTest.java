package com.designmodel.test;

import com.designmodel.Command.Command;
import com.designmodel.Command.LightCommand;
import com.designmodel.Command.Switch;

/**
 * Created by lisheng on 17-3-20.
 */
public class CommandTest {
    public static void main(String[] args) {
        Command command = new LightCommand();
        Switch witch = new Switch();
        witch.setCommand(command);
        witch.sendCommand();
    }
}
