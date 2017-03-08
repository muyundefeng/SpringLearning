package com.designmodel.singleton;

/**单例模式(在并发情况下不能保证是单例模式)
 * Created by lisheng on 17-3-8.
 */
public class TaskManager {

    private static TaskManager tm = null;

    private TaskManager(){}

    public static TaskManager getInstance(){
        if(tm == null){
            tm = new TaskManager();
        }
        return tm;
    }
}
