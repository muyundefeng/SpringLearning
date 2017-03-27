package com.designmodel.state;

/**
 * Created by lisheng on 17-3-27.
 */
public class RestrictedState extends State {
    public RestrictedState(Account account) {
        this.account = account;
    }

    @Override
    public void disposit(int money) {
        account.setBalance(account.getBalance() + money);
        stateCheck();
    }

    @Override
    public void withdraw(int money) {
        System.out.println("账号受到限制，无法执行该操作");
    }

    @Override
    public void computeInterest() {
        System.out.println("计算利息");
    }

    //状态之间的转化
    @Override
    public void stateCheck() {
        if (account.getBalance() < 0)
            account.setCurrentState(new OverdraftState(account));
        if (account.getBalance() > 0)
            account.setCurrentState(new NormalState(account));
    }
}
