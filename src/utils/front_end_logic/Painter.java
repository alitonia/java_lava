package utils.front_end_logic;

import utils.backend_logic.State;
import utils.front_end_logic.Colorful_Rectangle;

import java.util.List;

import static utils.consts.getFill;
import static utils.consts.getStroke;

// Handle some basic painting
public class Painter {

    public void paint_by_Status(Colorful_Rectangle rectangle) {
        rectangle.setFill(getFill(rectangle.getStatus()));
        rectangle.setStroke(getStroke(rectangle.getStatus()));
    }

    public void paint_by_Many_Status(List<State> stateList, List<Colorful_Rectangle> r) {
        Colorful_Rectangle rectangle;
        for (State s : stateList) {
            rectangle = r.get(s.getIndex());
            rectangle.setStatus(s.getStatus());
            paint_by_Status(rectangle);
        }
    }

    public void get_Delay() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
