package us.codecraft.webmagic.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.increment.DBUtil.DBUtil;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.component.DuplicateRemover;
import us.codecraft.webmagic.scheduler.component.HashSetDuplicateRemover;

/**
 * Remove duplicate urls and only push urls which are not duplicate.<br></br>
 *
 * @author code4crafer@gmail.com
 * @since 0.5.0
 */
public abstract class DuplicateRemovedScheduler implements Scheduler {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private DuplicateRemover duplicatedRemover = new HashSetDuplicateRemover();

    public DuplicateRemover getDuplicateRemover() {
        return duplicatedRemover;
    }

    public DuplicateRemovedScheduler setDuplicateRemover(DuplicateRemover duplicatedRemover) {
        this.duplicatedRemover = duplicatedRemover;
        return this;
    }
   
    public void push(Request request, Task task,boolean isDb) {
        logger.info("get a candidate url {}", request.getUrl());
        
        if (!duplicatedRemover.isDuplicate(request, task) || shouldReserved(request)) {
//        	DBUtil.saveDB(request.getUrl());//保存爬取过的链接
        	logger.info("push to queue {}", request.getUrl());
            pushWhenNoDuplicate(request, task,isDb);
        }
    }
//    
    //实现增量爬虫将请求过的url链接存放到
    //@Override
    /* (non-Javadoc)先
     * @see us.codecraft.webmagic.scheduler.Scheduler#push(us.codecraft.webmagic.Request, us.codecraft.webmagic.Task)
     */
//    public void push(Request request, Task task) {
//    	logger.info("access url is "+request.getUrl());
//    	logger.info("save url " +request.getUrl()+" to DB");
//    	DBUtil.saveDB(request.getUrl());
//    }

    protected boolean shouldReserved(Request request) {
        return request.getExtra(Request.CYCLE_TRIED_TIMES) != null;
    }

    protected void pushWhenNoDuplicate(Request request, Task task,boolean isDb) {

    }
}
