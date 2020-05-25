package utils;

import utils.backend_logic.State;
import utils.front_end_logic.Colorful_Rectangle;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static utils.consts.getFill;
import static utils.consts.getStroke;

// Handle some basic painting
public class Painter {
    //Single Thread to guarantee order of execution
    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    public synchronized void paint_by_Status(Colorful_Rectangle rectangle) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                rectangle.setFill(getFill(rectangle.getStatus()));
                rectangle.setStroke(getStroke(rectangle.getStatus()));
            }
        });

    }

    public void paint_by_Many_Status(List<State> stateList, List<Colorful_Rectangle> r) {
        //Debate-able use of Thread
        //Might not work consistently if Delay time too small
        final Colorful_Rectangle[] rectangle = new Colorful_Rectangle[1];
        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (State s : stateList) {
                    rectangle[0] = r.get(s.getIndex());
                    rectangle[0].setStatus(s.getStatus());
                    paint_by_Status(rectangle[0]);
                }
            }
        });

    }

}
