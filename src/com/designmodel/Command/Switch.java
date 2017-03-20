package com.designmodel.Command;

/**
 * 模拟开关，命令发送者
 * /**模拟开关，命令发送者
 * Created by lisheng on 17-3-20.
 */
public class Switch {
    public Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void sendCommand() {
        command.execute();
    }
}
