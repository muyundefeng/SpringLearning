package com.designmodel.factory;

import com.designmodel.entity.Logger;
import com.designmodel.entity.impl.DatabaseLogger;
import com.designmodel.entity.impl.FileLogger;

/**
 * 静态工厂方法设计模式(又称为简单工厂模式)
 * Created by lisheng on 17-3-8.
 */
public class LoggerFactory {
    public static Logger createLogger(String arg) {
        if (arg.equals("fileLogger"))
            return new FileLogger();
        if (arg.equals("databaseLogger"))
            return new DatabaseLogger();
        else
            return null;
    }

    public static void main(String[] args) {
        Logger logger = createLogger("fileLogger");
        System.out.println(logger.getClass());
    }
}
