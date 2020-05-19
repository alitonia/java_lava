package utils.front_end_logic;

import java.util.LinkedList;
import java.util.Queue;

import static utils.consts.*;

//Done
public class Log implements Runnable {

    private static Queue<String> writelog_Queue = new LinkedList<>();
    private static Thread t;

    public Log() {
        if (t == null) {
            System.out.println("No log running. Creating log");
            t = new Thread(this);
            t.setName("Log thread");
            t.setPriority(LOG_PRIORITY);
            t.setDaemon(true);
            System.out.println(t.getName() + " created");
            t.start();
        }

    }

    public void print(String msg) {
        writelog_Queue.offer(msg);
    }

    @Override
    public void run() {
        //The more message, the less it respond
        int rate = STANDARD_LOG_RATE;

        while (true) {
            if (!writelog_Queue.isEmpty()) {
                System.out.println(writelog_Queue.poll());
                rate = (int) Math.min(rate * RATE_INCREASE_COEFFICIENT, MAXIMUM_LOG_RATE);
            } else {
                rate = (int) Math.max(MINIMUM_LOG_RATE, rate * RATE_DECAY_COEFFICIENT);
            }
            try {
                Thread.sleep(rate * SLEEP_TIME_PER_RATE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
