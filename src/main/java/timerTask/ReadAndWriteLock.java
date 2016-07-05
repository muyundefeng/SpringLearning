package timerTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class ReadAndWriteLock {

	//Map<String, String> map;
	static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	static Lock rLock = readWriteLock.readLock();
	static Lock wLock = readWriteLock.writeLock();
	
	static class tasks implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			//rLock.lock();测试读取锁相关的执行效果,该锁不会导致线程同步,因为读取的时候会保证线程资源的不变性
			wLock.lock();//测试写锁执行的相关效果,使用该锁时,会执行线程同步,需要等待其他线程释放读取锁
			System.out.println("fetch this read lock");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			wLock.unlock();
			//rLock.unlock();
		}
		
	}
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		for(int i=0;i<10;i++){
			executorService.execute(new tasks());
		}
		executorService.shutdown();
	}
	
	
}
