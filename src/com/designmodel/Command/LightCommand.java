package com.designmodel.Command;

/**
 * Created by lisheng on 17-3-20.
 */
public class LightCommand extends Command {
    Receiver receiver = new LightReceiver();

    @Override
    public void execute() {
        receiver.execute();
    }
}
