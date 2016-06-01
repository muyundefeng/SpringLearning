package com.threadpool.muyundfeng;

import java.util.concurrent.LinkedBlockingQueue;

public class WaitAndNotify extends Thread{
	
	LinkedBlockingQueue<String> list=new LinkedBlockingQueue<String>(3);
	
	public boolean isFull()
	{
		if(list.size()<3)
			return false;
		return true;
	}
	public boolean isEmpty()
	{
		return list.isEmpty();
	}
	//��queue�����Ԫ��
	public synchronized void putElement(String str) throws InterruptedException
	{
		while(isFull())
			wait();//���ø÷�����ʹ�õ��������ж��������������Ķ���һ�£���ʱwait����this��sychronized����Ҳ��this
		list.add(str);
		System.out.println("add elment "+str);
		notifyAll();
	}
	//�Ӷ������Ƴ�Ԫ��
	public synchronized void takeElement(String str) throws InterruptedException
	{
		while(isEmpty())
			wait();
		list.remove(str);
		System.out.println("remove elment "+str);
		notifyAll();
		
	}
	public void run()
	{
		if(Thread.currentThread().getName().contains("0"))
		{
			try {
				takeElement("hello");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else{
			try {
				putElement("hello");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(list);
	}
	
	public static void main(String []args)
	{
		WaitAndNotify thread=new WaitAndNotify();
		for(int i=0;i<3;i++)
		{
			Thread thread2=new Thread(thread,"thread"+i);
			thread2.start();
		}
		
	}

}
