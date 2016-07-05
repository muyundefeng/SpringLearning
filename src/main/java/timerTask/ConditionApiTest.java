package timerTask;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
/*
 * 如果想要构造多个条件队列,可以使用LOck与condition类进行相关的创建
 * 一定要记住这两个类要配合使用,不能只是其中一个.线程执行原理与前面的内置锁与内置条件队列
 * 一致
 */
public class ConditionApiTest implements Runnable{

	final Queue<String> queue = new LinkedBlockingQueue<String>(5);
	final Lock lock1 = new ReentrantLock();
	private Condition notFull = lock1.newCondition();
	private Condition notEmpty = lock1.newCondition();
	
	public void put(String str) throws InterruptedException{
		lock1.lock();
		while(queue.size()>=5)
		{
			System.out.println(Thread.currentThread().getName()+" waiting for space");
			notFull.await();
		}
		queue.add(str);
		notEmpty.signal();
		lock1.unlock();
	}
	public void get() throws InterruptedException{
		lock1.lock();
		while(queue.isEmpty())
		{
			System.out.println(Thread.currentThread().getName()+" waiting for elements");
			notEmpty.await();
		}
		System.out.println("fetch elements is "+queue.remove());
		notFull.signal();
		lock1.unlock();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			put("hello");
			get();
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		for(int i=0;i<5;i++)
		{
			executorService.execute(new ConditionApiTest());
		}
		executorService.shutdown();
	}
	
	
}
