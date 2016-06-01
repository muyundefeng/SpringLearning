
public class NoVisibility {

	private static boolean ready;
	private static int number;
	
	private static class ReaderThread extends Thread{
		public void run()
		{
			while(!ready)
			{
				System.out.println(ready);
				Thread.yield();//Ïß³Ì¹ÒÆð
				
			}
			System.out.println(number);
		}
	}
	
	public static void main(String[]args)
	{
		new ReaderThread().start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		number = 42;
		ready = true;
	}
}
