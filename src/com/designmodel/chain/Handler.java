package com.designmodel.chain;

/**
 * 关于链式请求如下图所示:
 * --handler1--handler2--handler3--handler4--...
 * -->(request)
 * 如上面所示:请求从hander1传递到handler4，如果中间的某个handler可以处理该请求，则停止请求的传递，否则传递该请求给下一个
 * handler
 *所以定义抽象处理对象，该对象持有一个Handler的引用以及请求处理方法
 *
 * 链式请求的链的创建是由客户端创建，具体代码参照ChainTest
 * (模式中的角色介绍：
 * ● Handler（抽象处理者）：它定义了一个处理请求的接口，一般设计为抽象类，由于不同的具体处理者处理请求的方式不同，
 *  因此在其中定义了抽象请求处理方法。因为每一个处理者的下家还是一个处理者，因此在抽象处理者中定义了一个抽象处理者类型的对象，
 *  作为其对下家的引用。通过该引用，处理者可以连成一条链。
    ● ConcreteHandler（具体处理者）：它是抽象处理者的子类，可以处理用户请求，在具体处理者类中实现了抽象处理者中定义的抽象请求处理方法，
    在处理请求之前需要进行判断，看是否有相应的处理权限，如果可以处理请求就处理它，否则将请求转发给后继者；在具体处理者中可以访问链中下一个对象，
    以便请求的转发。
 * )
 * Created by lisheng on 17-3-20.
 */
public abstract class Handler {

    Handler handler;

    public abstract void handleRequest(int money);
    public abstract void setNextObject(Handler handler);
}
