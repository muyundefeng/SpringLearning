package com.designmodel.mediator;

import java.util.Date;

/**模拟这样一个类似的场景
 * 多个对象之间进行交互，比如说在qq群中，一个用户可能与其他多个用户存在关系，是一个多对多的关系
 * 为了简化对象之间的关联关系，使用中介者对象来处理对象之间的交互。
 *
 * 意图：用一个中介对象来封装一系列的对象交互，中介者使各对象不需要显式地相互引用，从而使其耦合松散，而且可以独立地改变它们之间的交互。
 主要解决：对象与对象之间存在大量的关联关系，这样势必会导致系统的结构变得很复杂，同时若一个对象发生改变，我们也需要跟踪与之相关联的对象，同时做出相应的处理。
 何时使用：多个类相互耦合，形成了网状结构。
 如何解决：将上述网状结构分离为星型结构。
 关键代码：对象 Colleague 之间的通信封装到一个类中单独处理。
 应用实例： 1、中国加入 WTO 之前是各个国家相互贸易，结构复杂，现在是各个国家通过 WTO 来互相贸易。 2、机场调度系统。 3、MVC 框架，其中C（控制器）就是 M（模型）和 V（视图）的中介者
 * Created by lisheng on 17-3-27.
 */
public class ChatRoom {
    public static void showMessage(User user, String message){
        System.out.println(new Date().toString()
                + " [" + user.getName() +"] : " + message);
    }
}
