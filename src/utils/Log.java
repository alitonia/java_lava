package utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Done
public class Log {
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    public Log() {
    }

    public void print(String msg) {
        executor.execute(() -> System.out.println(msg));
    }
}
