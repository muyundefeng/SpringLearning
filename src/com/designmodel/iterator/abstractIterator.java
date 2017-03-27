package com.designmodel.iterator;

/**遍历模式
 * 在遍历器模式，需要定义相关的抽象操作
 * 在实践过程中直接使用java自带的Iterator
 * 对象进行便利操作即可。阅读相关的java api
 * Created by lisheng on 17-3-27.
 */
public interface abstractIterator {

    public void next(); //移至下一个元素
    public boolean isLast(); //判断是否为最后一个元素
    public void previous(); //移至上一个元素
    public boolean isFirst(); //判断是否为第一个元素
    public Object getNextItem(); //获取下一个元素
    public Object getPreviousItem(); //获取上一个元素
}
