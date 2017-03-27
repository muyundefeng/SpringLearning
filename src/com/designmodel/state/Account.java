package com.designmodel.state;

/**
 * 模拟context环境类，是state类的真正的持有者
 * <p>
 * 环境类维持一个对抽象状态类的引用，
 * 通过setState()方法可以向环境类注入不同的状态对象，再在环境类的业务方法中调用状态对象的方法
 * <p>
 * 在状态模式的使用过程中，一个对象的状态之间还可以进行相互转换，通常有两种实现状态转换的方式：
 * (1) 统一由环境类来负责状态之间的转换，此时，环境类还充当了状态管理器(State Manager)角色，
 * 在环境类的业务方法中通过对某些属性值的判断实现状态转换，
 * (2) 由具体状态类来负责状态之间的转换，可以在具体状态类的业务方法中判断环境类的某些属性值再根据情况为环境类设置新的状态对象，
 * 实现状态转换，同样，也可以提供一个专门的方法来负责属性值的判断和状态转换。此时，
 * 状态类与环境类之间就将存在依赖或关联关系，因为状态类需要访问环境类中的属性值
 * Created by lisheng on 17-3-27.
 */
public class Account {

    public State currentState;

    public String owner;//银行账户的持有者

    public int balance = 0;//银行账户的余额，初始值为0;

    public Account(String owner, int balance) {
        this.owner = owner;
        this.balance = balance;
        this.currentState = new NormalState(this);
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    //模拟银行账户取款
    public void withdraw(int money) {
        System.out.println("从" + this.owner + "账户取出现金:" + money);
        currentState.withdraw(money);
        System.out.println("现在账户余额为：" + this.balance);
        System.out.println("现在帐户状态为" + this.currentState.getClass().getName());
        System.out.println("--------------------------------------");
    }

    //模拟银行账户存款
    public void desposit(int money) {
        System.out.println("向" + this.owner + "存放" + money);
        currentState.disposit(money);
        System.out.println("现在账户余额为：" + this.balance);
        System.out.println("现在帐户状态为" + this.currentState.getClass().getName());
        System.out.println("--------------------------------------");
    }

    public void computeInterest() {
        currentState.computeInterest(); //调用状态对象的computeInterest()方法
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
