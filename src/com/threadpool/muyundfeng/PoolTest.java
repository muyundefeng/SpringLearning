package com.threadpool.muyundfeng;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PoolTest {
	public final static int THREADNUMBRE = 2;
	
	public final static int MAX_Threads = 3;
	
	public final static long KEEPALIVE = 1000;
	
	public final static int capaicity = 1;
	
	public final static TimeUnit TIMEUNIT = TimeUnit.SECONDS;
	
	public final static LinkedBlockingQueue<Runnable> BLOCKING_QUEUE=new LinkedBlockingQueue<Runnable>(capaicity) ;//创建一个有界队列
	
	ThreadPoolExecutor executor=new ThreadPoolExecutor(THREADNUMBRE, MAX_Threads, KEEPALIVE, TIMEUNIT, BLOCKING_QUEUE);
	
	public void init()
	{
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
	}
	
	class myThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+"is running");
			
		}
		
	}
	public  void startThread()
	{
		myThread mThread = new myThread();
		//Thread thread = new Thread(mThread);
		for(int i=0;i<5;i++)
		{
			Thread thread = new Thread(mThread);
			executor.execute(thread);
		}
		executor.shutdown();
	}
	public static void main(String[]args)
	{
		PoolTest poolTest=new PoolTest();
		poolTest.init();
		poolTest.startThread();
	}

}
