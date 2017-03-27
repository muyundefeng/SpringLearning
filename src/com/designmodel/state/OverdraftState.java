package com.designmodel.state;

/**
 * 模拟银行账户的透支状态
 * Created by lisheng on 17-3-27.
 */
public class OverdraftState extends State {

    public OverdraftState(Account account) {
        this.account = account;
    }

    @Override
    public void disposit(int money) {
        account.setBalance(account.getBalance() + money);
        stateCheck();
    }

    @Override
    public void withdraw(int money) {
        account.setBalance(account.getBalance() - money);
        stateCheck();
    }

    @Override
    public void computeInterest() {
        System.out.println("计算利息");
    }

    //进行状态之间的转化
    @Override
    public void stateCheck() {
        if (account.getBalance() < -2000)
            account.setCurrentState(new RestrictedState(account));
        else if (account.getBalance() > -2000 && account.getBalance() < 0)
            account.setCurrentState(new OverdraftState(account));
        else if (account.getBalance() > 0)
            account.setCurrentState(new NormalState(account));
    }
}
