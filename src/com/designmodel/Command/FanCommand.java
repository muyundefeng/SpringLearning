package com.designmodel.Command;

/**
 * Created by lisheng on 17-3-20.
 */
public class FanCommand extends Command {
    Receiver receiver = new FanReceiver();;

    @Override
    public void execute() {
        receiver.execute();
    }
}
