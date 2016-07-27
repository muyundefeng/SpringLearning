package com.github.muyundfeng.main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class myThreadPool {

	public int ThreadNumber;
	public int defaultSize = 2;
	public Lock lock = new ReentrantLock();
	public final Condition condition = lock.newCondition();
	//public final Condition singalCondition = lock.newCondition();
	public AtomicInteger aliveNumber = new AtomicInteger(0);//设置当前存活的线程数量
	private ExecutorService exec ;//创建具有十个线程的线程池
    private Logger logger = LoggerFactory.getLogger(getClass());

	
	public myThreadPool(int threadNumber,int size) {
		// TODO Auto-generated constructor stub
		this.ThreadNumber = threadNumber;
		aliveNumber.set(0);
		exec = size!=0?Executors.newFixedThreadPool(size):Executors.newFixedThreadPool(defaultSize);
	}
	public int getAliveNumber(){
		logger.info("the number is "+aliveNumber.get());
		return aliveNumber.get();
	}
	
	public void shudowm(){
		exec.shutdown();
	}
	
	public void execute(final Runnable runnable) {
	    	
        if (aliveNumber.get() >= ThreadNumber) {
            try {
                lock.lock();
                while (aliveNumber.get() >= ThreadNumber) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                    }
                }
            } finally {
            	lock.unlock();
            }
        }
        aliveNumber.incrementAndGet();
        exec.execute(new Runnable() {
            //@Override
            public void run() {
                try {
                    logger.info("current thread is "+Thread.currentThread().getName());
                	runnable.run();
                } finally {
                    try {
                        lock.lock();
                        aliveNumber.decrementAndGet();
                        condition.signal();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        });
    }
	/*
	public void execute(Runnable task){
		//当存活的线程数量大于线程池zh
		if(aliveNumber.get() >= ThreadNumber){
			lock.lock();
			logger.info("wait for singal");
			try {
				condition.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lock.unlock();
		}
		aliveNumber.incrementAndGet();
		exec.execute(new Runnable() {
			@Override
			public void run() {
				task.run();
				lock.lock();
				aliveNumber.decrementAndGet();
				condition.signal();
				lock.unlock();
			}
		});
	}*/
	
}
