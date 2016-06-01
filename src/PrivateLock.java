class A{}



public class PrivateLock extends Thread
{
	private final Object myLock=new Object();
	int shareVlaue=1;
	
	public void run()
	{
		someMethod();
	}
	public void someMethod()
	{
		synchronized (myLock) {
			while(shareVlaue<10)
			{
				shareVlaue++;
				System.out.println(shareVlaue);
			}
		}
	}
	public static  void main(String[]args)
	{
		PrivateLock locak=new PrivateLock();
		//PrivateLock locak1=new PrivateLock();
		Thread thread1=new Thread(locak);
		Thread thread2=new Thread(locak);
		thread1.start();
		thread2.start();
	}
	
	}