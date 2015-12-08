package com.anders.ethan.log.client.api;

import com.anders.ethan.log.client.api.spi.ITraceBinder;
import com.anders.ethan.log.client.api.spi.ITraceFactory;

public class StaticTraceGenBinder implements ITraceBinder {
    //单例
    private static final StaticTraceGenBinder SINGLETON = new StaticTraceGenBinder();

    public static final String requiredVersion = "0.1";

    private StaticTraceGenBinder() {
        throw new UnsupportedOperationException("This code should have never made it into mercury-client-api.jar");
    }

    @Override
    public String getRequiredVersion() {
        throw new UnsupportedOperationException("This code should have never made it into mercury-client-api.jar");
    }

    public static StaticTraceGenBinder SINGLETON() {
        return SINGLETON;
    }

    @Override
    public ITraceFactory getTraceFactory() {
        throw new UnsupportedOperationException("getTraceFactory");
    }

    @Override
    public String getTraceGenStr() {
        throw new UnsupportedOperationException("This code should have never made it into mercury-client-api.jar");
    }
}
