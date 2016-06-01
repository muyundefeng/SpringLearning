/*
 * 参照程序，采用同步机制的程序与该原始程序作对比
 */

public class CompareClass extends Thread{
	
	public  static int shareValue = 1;
	public void run()
	{
		//system(this.getName());
		synchronized(this)
		{
			while(shareValue<10)
			{
				shareValue++;
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()+"'s value is="+shareValue);
			}
		}
	}
//	public synchronized void system(String str)
//	{
//		while(shareValue<10)
//		{
//			shareValue++;
//			System.out.println(str+"'s value is="+shareValue);
//		}
//		
//	}
	public static void main(String[]args)
	{
//		int i = 0;
//		while(i < 3)
//		{
//			i++;
			CompareClass compareClass = new CompareClass();
			Thread thread1=new Thread(compareClass, "Thread1");
			Thread thread2=new Thread(compareClass, "Thread2");
			thread1.start();
			thread2.start();
			
	}
}
