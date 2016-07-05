package timerTask;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentLockTest {
	static Lock lock = new ReentrantLock();//定义全局变使得所有线程获得同一把锁
	static class tasks implements Runnable {
		int sum;
		public tasks(int sum) {
			// TODO Auto-generated constructor stub
			this.sum = sum;
		}
		public void run() {
			
			lock.lock();
			sum++;
			System.out.println("sum is = "+sum);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				lock.unlock();
			}
		}
	}
	public static void main(String[] args) {
		for(int i=0;i<5;i++){
			new Thread(new tasks(10)).start();
		}
	}
}
