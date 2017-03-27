package com.designmodel.test;

import com.designmodel.state.Account;

/**状态模式客户端
 * Created by lisheng on 17-3-27.
 */
public class StateTest {
    public static void main(String[] args) {
        Account account = new Account("tom",1000);
        account.withdraw(3000);
        account.withdraw(2000);
        account.desposit(6000);
        account.desposit(100);
    }
}
