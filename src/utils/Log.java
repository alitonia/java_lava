package utils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static utils.consts.*;

//Done
public class Log {
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    public Log() {
    }

    public void print(String msg) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(msg);
            }
        });
    }
}
