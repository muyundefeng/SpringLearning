package com.designmodel.factory.impl;

import com.designmodel.entity.Logger;
import com.designmodel.entity.impl.DatabaseLogger;
import com.designmodel.factory.LoggerFactoryPattern;

/**
 * Created by lisheng on 17-3-8.
 */
public class DatabaseLoggerFactory implements LoggerFactoryPattern {
    @Override
    public Logger createLogger() {
        return new DatabaseLogger();
    }
}
