/*
 * ��������е�ջ��ÿ���߳�ά���Լ���ջ�ռ䣬���÷����еı�������ռ�ռ䣬���������ص�ͬ�����ƣ������й������
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
