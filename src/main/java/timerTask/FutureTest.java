package timerTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class FutureTest {

	static class task implements Callable<String>{
		int sum;
		int add;
		public task(int sum){
			this.sum = sum;
			this.add = 0;
		}
		@Override
		public String call() throws Exception {
			// TODO Auto-generated method stub
			
			for(int i = 0;i<sum;i++){
				add +=sum;
				
			}
			System.out.println("running subThread");
			Thread.sleep(2000);
			return add+"";
		}
		
	}
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		task newtask = new task(10);
		Future<String> future = executorService.submit(newtask);
		try {
			String result = future.get();
			System.out.println("the result is "+result);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		executorService.shutdown();
		
	}
}
