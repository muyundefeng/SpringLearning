import java.util.concurrent.CountDownLatch;

class Task implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i=0;i<100;i++)
		{}
	}
	
}
public class TestHarness {

	public static long timeTasks(int nThread,final Runnable task) throws InterruptedException
	{
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(nThread);
		
		for(int i=0;i<nThread;i++)
		{
			Thread thread=new Thread(){
				public void run(){
					try{
						task.run();
						startGate.await();
					}
					catch(InterruptedException e)
					{}
					finally {
						endGate.countDown();
					}
				}
			};
			thread.start();
		}
		long start = System.nanoTime();
		startGate.countDown();
		endGate.await();
		long end=System.nanoTime();
		System.out.println("Runing time is "+(end-start));
		return end-start;
	}
	public static void main(String[]args) throws InterruptedException
	{
		Task task=new Task();
		timeTasks(4,task);
	}
}
