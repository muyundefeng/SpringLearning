package timerTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentraLockTest2 {

	static Lock rlock = new ReentrantLock();
	static Lock wLock = new ReentrantLock();
	static class tasks implements Runnable{

		@Override
		public void run()  {
			// TODO Auto-generated method stub
			try {
				//需要设置获取锁的时间的最大延迟,如果不设置相关时间延迟,第二个线程在执行这句代码的时候,会立刻返回false,线程执行失败.时间设置的大小要根据线程的执行时间来进行相关的划分.
				if(rlock.tryLock(30,TimeUnit.SECONDS)){
					System.out.println(Thread.currentThread().getName()+"Fectch the read lock ,Reading something from context!");
					if(wLock.tryLock(21,TimeUnit.SECONDS)){
						System.out.println(Thread.currentThread().getName()+"Fetch the write lock successfully,writing the something");
						System.out.println(Thread.currentThread().getName()+"Writing....");
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						finally{
							System.out.println(Thread.currentThread().getName()+"Release the write lock");
							wLock.unlock();
						}
					}
					System.out.println(Thread.currentThread().getName()+"Reading....");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					finally {
						{
							System.out.println(Thread.currentThread().getName()+"Release the Read lock");
							rlock.unlock();
						}
					}
					
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//return "hello world";
		}
		
	}
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		for(int i = 0;i<3;i++){
			executorService.execute(new tasks());
		}
		executorService.shutdown();
	}
}
