package com.designmodel.state;

/**
 * 在抽象状态类的子类即具体状态类中实现了在抽象状态类中声明的业务方法，
 * 不同的具体状态类可以提供完全不同的方法实现，在实际使用时，在一个状态类中可能包含多个业务方法，
 * 如果在具体状态类中某些业务方法的实现完全相同，可以将这些方法移至抽象状态类，实现代码的复用
 * 模拟正常状态
 *
 * Created by lisheng on 17-3-27.
 */
public class NormalState extends State {

    public NormalState(Account account) {
        this.account = account;
    }

    @Override
    public void disposit(int money) {
        account.setBalance(account.getBalance() + money);
    }

    @Override
    public void withdraw(int money) {
        account.setBalance(account.getBalance() - money);
        //取款操作之后要检查整个银行账户的状态
        stateCheck();
    }

    @Override
    public void computeInterest() {
        System.out.println("正常操作，没有利息");
    }

    @Override
    public void stateCheck() {
        if (account.getBalance() < 0 && account.getBalance() > -2000)
            account.setCurrentState(new OverdraftState(account));
        else if (account.getBalance() < -2000)
            account.setCurrentState(new RestrictedState(account));
    }
}
