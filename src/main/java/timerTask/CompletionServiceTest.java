package timerTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletionServiceTest {

	static int poolSize = 10;
	static ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
	static ExecutorCompletionService<String> executorCompletionService = new ExecutorCompletionService<String>(executorService);
	static int numThread = 5;
	static class tasks implements Callable<String>{
		int sum;
		public tasks(int sum){
			this.sum = sum;
		}
		@Override
		public String call() throws Exception {
			// TODO Auto-generated method stub
			return sum + "";
		}
		
	}
	
	public static void main(String[] args) {
		//Future<String> future = executorCompletionService.submit(new tasks(10));
		System.out.println("执行主函数main.....");
		try {
			//String result = future.get();
			for(int i = 0;i<numThread;i++ ){  
				executorCompletionService.submit(new tasks(i));  //当提交多个任务的时候,产生的结果提交到完成队列中去
		      }   
		          
	        for(int i = 0;i<numThread;i++ ){       
	            System.out.println(executorCompletionService.take().get()); //executor将产生的结果存放在阻塞队列中去
	            //采用take方法可以获取一个future任务
	        }  
			//System.out.println("得到执行结果"+result);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			executorService.shutdown();
		}
		
	}
}
