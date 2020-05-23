package utils;

import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class consts{

    public static final int MAX_EXECUTIONERS = 4;
    //Thread Priority
    public static final int LOG_PRIORITY = 8;


    // Names:
    public static final String SEQUENTIAL = "Sequential Search";
    public static final String BINARY = "Binary Search";
    public static final String A_Star = "A* Path-finding";

    //Operating mode:
    public static final int SEQUENTIAL_MODE = 0;
    public static final int BINARY_MODE = 1;
    public static final int A_STAR_MODE = 2;

    //Data properties
    public static final int NUMBER_OF_RECTANGLE = 20;
    public static final int LENGTH_OF_2D = 20;
    public static final int WIDTH_OF_2D = 20;

    //Colorful Rect properties
    //color
    public static final Color NORMAL_FILL_COLOR =
            new Color(0.158, 0.89, 0.96, 0.75);
    public static final Color NORMAL_STROKE_COLOR =
            new Color(0.667, 0.96, 0.189, 1);

    // size
    public static final double MINIMUM_RECT_HEIGHT = 0.1D;
    public static final double MAXIMUM_RECT_HEIGHT = 0.97D;
    //status
    public static final int NORMAL_RECT_STATUS = 0;
    public static final int SOMEWHAT_SPECIAL_RECT_STATUS = -10;


    //Add more when have more status

    public static HashMap<Integer, List<Color>> COLORS_ARRAY = new HashMap<Integer, List<Color>>();
    static {
        COLORS_ARRAY.put(NORMAL_RECT_STATUS, Arrays.asList(NORMAL_FILL_COLOR, NORMAL_STROKE_COLOR));
        COLORS_ARRAY.put(SOMEWHAT_SPECIAL_RECT_STATUS, Arrays.asList(NORMAL_STROKE_COLOR, NORMAL_FILL_COLOR));
    }

    private static final int VALUE_OF_FILL_COLOR = 0;
    private static final int VALUE_OF_STROKE_COLOR = 1;

    //Log parameters
    public static final int MAXIMUM_QUEUING_MESSAGE = 50;

    public static final int STANDARD_LOG_RATE = 1;
    public static final int MAXIMUM_LOG_RATE = 1000;
    public static final int MINIMUM_LOG_RATE = 0;

    public static final float RATE_INCREASE_COEFFICIENT = 1.3F;
    public static final float RATE_DECAY_COEFFICIENT = 0.5F;

    public static final int SLEEP_TIME_PER_RATE = 10;


    public static Color getFill(int status){
        try {
            return COLORS_ARRAY.get(status).get(VALUE_OF_FILL_COLOR);
        } catch (Exception e){
            System.out.println("Can't get color");
        }
        return new Color(0,0,0,0);
    }


    public static Color getStroke(int status){
        try {
            return COLORS_ARRAY.get(status).get(VALUE_OF_STROKE_COLOR);
        } catch (Exception e){
            System.out.println("Can't get color");
        }
        return new Color(0,0,0,0);
    }
}

