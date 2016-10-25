package us.codecraft.webmagic.scheduler;

import org.apache.http.annotation.ThreadSafe;

import com.appCrawler.increment.DBUtil.DBUtil;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Basic Scheduler implementation.<br>
 * Store urls to fetch in LinkedBlockingQueue and remove duplicate urls by HashMap.
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.1.0
 */
@ThreadSafe
public class QueueScheduler extends DuplicateRemovedScheduler implements MonitorableScheduler {

    private BlockingQueue<Request> queue = new LinkedBlockingQueue<Request>();

    @Override
    public void pushWhenNoDuplicate(Request request, Task task,boolean isDb) {
//    	logger.info(queue.size()+"");
    	//将带抓取队列中的数据保存至数据库，存放字符串类型，每次队列更新，同步到redis数据库
//    	for(Request request2:queue){
//    		System.out.println("access queue is"+request2.getUrl().toString());
//    	}
        queue.add(request);
        if(!isDb)
        	DBUtil.saveToAccessUrlDB(queue);
    }

    //@Override
    public synchronized Request poll(Task task) {
        return queue.poll();
    }

    //@Override
    public int getLeftRequestsCount(Task task) {
        return queue.size();
    }

   // @Override
    public int getTotalRequestsCount(Task task) {
        return getDuplicateRemover().getTotalRequestsCount(task);
    }
    
    public void cleanDB(Task task){
    	queue.clear();
    }
    
    public void cleanSet(Task task){
    	getDuplicateRemover().resetDuplicateCheck(task);
    }
    
    public void push(Request request){
    	
    }
    public boolean isEmpty(){
    	return queue.isEmpty();
    }
}
