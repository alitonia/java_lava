package utils.front_end_logic;

import utils.front_end_logic.Colorful_Rectangle;

import static utils.consts.getFill;
import static utils.consts.getStroke;

// Handle some basic painting
public class Painter {

    public void paint_by_Status(Colorful_Rectangle rectangle) {
        rectangle.setFill(getFill(rectangle.getStatus()));
        rectangle.setStroke(getStroke(rectangle.getStatus()));
    }


}
