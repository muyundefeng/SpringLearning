package com.threadpool.muyundfeng;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest extends Thread{

	//创建锁
	public static final ReentrantLock reentranrtlock = new ReentrantLock();
	
	//生成该锁的相关条件谓词
	public final Condition isFull = reentranrtlock.newCondition();//该条件谓词与内置对象锁（wait,notify）一致，条件谓词与reentranlock属于同一个锁对象
	
	public final Condition isEmpty = reentranrtlock.newCondition();
	
	public LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<String>();
	
	public boolean isFull()
	{
		if(linkedBlockingQueue.size()<3)
			return false;
		return true;
	}
	
	public boolean isEmpty()
	{
		return linkedBlockingQueue.isEmpty();
	}
	
	public void put(String str) throws InterruptedException
	{
		reentranrtlock.lock();
		while (isFull()) 
			isFull.await();
		linkedBlockingQueue.add(str);
		System.out.println("添加"+str);
		isFull.signal();
		System.out.println(linkedBlockingQueue);
		reentranrtlock.unlock();
	}
	
	public void take(String str) throws InterruptedException
	{
		reentranrtlock.lock();
		while(isEmpty())
			isEmpty.await();
		linkedBlockingQueue.remove(str);
		isEmpty.signal();
		System.out.println("获取"+str);
		System.out.println(linkedBlockingQueue);
		reentranrtlock.unlock();
	}
	
	public void run(){
		String threadName = Thread.currentThread().getName();
		if(threadName.contains("0")||threadName.contains("1"))
		{
			try {
				take("hello");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(threadName.contains("2")||threadName.contains("3"))
		{
			try {
				put("hello");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public static void main(String[]args)
	{
		ConditionTest conditionTest = new ConditionTest();
		for(int i=0;i<4;i++)
		{
			Thread thread = new Thread(conditionTest,"thread"+i);
			thread.start();
		}
	}
}
