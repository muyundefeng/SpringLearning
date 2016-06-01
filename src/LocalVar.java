/*
 * 并发编程中的栈，每个线程维护自己的栈空间，调用方法中的变零属于占空间，无需进行相关的同步机制，除非有共享变零
 */
public class LocalVar extends Thread{
	
	public void run()
	{
		system(this.getName());
	}
	public void system(String str)
	{
		int a=0;
		while(a<10)
		{
			a++;
			System.out.println(str+" a's value is="+a);
		}
		
	}
	public static void main(String[]args)
	{
		int i=0;
		while(i<3)
		{
			i++;
			LocalVar localVar=new LocalVar();
			localVar.setName("Thread"+i);
			localVar.start();
		}
	}

}
