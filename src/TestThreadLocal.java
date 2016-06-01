/*
 * ��������е�ջ��
 * ÿ���̶߳�ά���Լ���ThreadLocal�������൱�ڸ�ÿ���̴߳�����һ���ֲ�����
 */
public class TestThreadLocal extends Thread{
	public static int shareValue;
	public static ThreadLocal<Integer> threadLocal= new ThreadLocal<Integer>(){
		protected Integer initialValue()
		{
			return Integer.valueOf(shareValue);
		}
	};
	
	public void run()
	{
		int a=0;
		while(a<10)
		{
			a++;
			threadLocal.set(a);
			System.out.println(Thread.currentThread().getName()+"'s value is="+threadLocal.get());
		}
	}
	
	public static void main(String[]args)
	{
//		int i=0;
//		while(i<3)
//		{
//			i++;
			TestThreadLocal localVar=new TestThreadLocal();
			Thread thread1=new Thread(localVar,"Thread1");
			Thread thread2=new Thread(localVar,"Thread2");
			thread1.start();
			thread2.start();
		//}
	}

}
