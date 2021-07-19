package com.example.inshortsv1;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppController {

    private static AppController instance;

    public static AppController getInstance()
    {
        if(instance == null)
        {
            instance = new AppController();
        }
        return instance;
    }

    private final ScheduledExecutorService networkhandler =
            Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService getNetworkhandler()
    {
        return  networkhandler;
    }
}
