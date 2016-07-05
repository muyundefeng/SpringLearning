package timerTask;
/*
 * 内置条件队列相关测试,如果想要调用内置条件对象相关的api方法,必须首先获得相关的对象锁(内置锁),
 * 每个java对象维护一个内置条件队列,因为条件队列与java对象绑定在一起,所以一个java对象
 * 维护者一个条件队列,也就说一个内置锁也维护着一个条件队列.如果想要避免这种限制,可以使用Lock和
 * Condition显示控制线程的执行.
 */
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ConditionQueueTest implements Runnable{
	
	static Queue<String> queue = new LinkedBlockingQueue<String>(5);
	
	public synchronized void putValue(String str) throws InterruptedException{
		while(queue.size()>=5)
		{
			System.out.println(Thread.currentThread().getName()+"the thread is stopped,waiting for space");
			wait();
		}
		queue.add(str);
		notifyAll();//  唤醒在此对象监视器上等待的所有线程。
	}
	public synchronized void getValue() throws InterruptedException{
		while(queue.isEmpty())
		{
			System.out.println(Thread.currentThread().getName()+"is stopped,waiting for elements");
			wait();
		}
		System.out.println(Thread.currentThread().getName()+"remove element is "+queue.remove());
		notifyAll();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			putValue("hello");
			getValue();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		ConditionQueueTest conditionQueueTest = new ConditionQueueTest();
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		for(int i=0;i<6;i++)
		{
			executorService.execute(conditionQueueTest);
		}
		executorService.shutdown();
	}
	
}
