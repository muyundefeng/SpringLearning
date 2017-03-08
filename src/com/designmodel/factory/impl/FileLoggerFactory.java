package com.designmodel.factory.impl;

import com.designmodel.entity.Logger;
import com.designmodel.entity.impl.FileLogger;
import com.designmodel.factory.LoggerFactoryPattern;

/**工厂方法模式(并不是静态工厂方法模式)
 * 一个具体的工厂只会生成一种产品(该类只能返回FileLogger)
 * Created by lisheng on 17-3-8.
 */
public class FileLoggerFactory implements LoggerFactoryPattern {
    @Override
    public Logger createLogger() {
        return new FileLogger();
    }
}
