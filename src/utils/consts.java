package utils;

import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class consts {

    public static final int MAX_EXECUTIONERS = 4;
    //Thread Priority
    public static final int LOG_PRIORITY = 8;

    //Play-speed

    public static final long DELAY_MILIS_PER_RUN = 500;
    // Names:
    public static final String SEQUENTIAL = "Sequential Search";
    public static final String BINARY = "Binary Search";
    public static final String A_Star = "A* Path-finding";

    //Operating mode:
    public static final int SEQUENTIAL_MODE = 0;
    public static final int BINARY_MODE = 1;
    public static final int A_STAR_MODE = 2;

    //Data properties
    public static final int NUMBER_OF_RECTANGLE = 200;
    public static final int NUMBER_OF_RECTANGLE_HORIZONTAL = 20;
    public static final int NUMBER_OF_RECTANGLE_VERTICAL = 20;

    //Colorful Rect properties
    //color
    private static float red = 0;
    private static float green = 0;
    private static float blue = 0;
    private static float opacity = 0;

    public static final Color NORMAL_FILL_COLOR =
            new Color(0.158, 0.89, 0.96, 0.75);
    public static final Color NORMAL_STROKE_COLOR =
            new Color(0.667, 0.96, 0.189, 1);

    public static final Color SOMEWHAT_SPECIAL_FILL_COLOR =
            new Color(0.17, 0.85, 0.459, 1);
    public static final Color SOMEWHAT_SPECIAL_STROKE_COLOR =
            new Color(0.158, 0.89, 0.96, 0.75);

    public static final Color GATE_FILL_COLOR =
            new Color((float) 124 / 255, (float) 64 / 255, (float) 33 / 255, 1);
    public static final Color GATE_STROKE_COLOR =
            new Color((float) 231 / 255, (float) 20 / 255, (float) 20 / 255, 0.25);

    public static final Color OBSTACLE_FILL_COLOR =
            new Color((float) 231 / 255, (float) 20 / 255, (float) 20 / 255, 1);
    public static final Color OBSTACLE_STROKE_COLOR =
            new Color((float) 18 / 255, (float) 148 / 255, (float) 127 / 255, 0.25);

    public static final Color THE_CHOSEN_FILL_COLOR =
            new Color(0.667, 0.96, 0.189, 1);
    public static final Color THE_CHOSEN_STROKE_COLOR =
            new Color(0.158, 0.89, 0.96, 0.75);

    public static final Color THE_TRUMP_FILL_COLOR =
            new Color((float) 18 / 255, (float) 25 / 255, (float) 250 / 255, 0.8);
    public static final Color THE_TRUMP_STROKE_COLOR =
            new Color(0.667, 0.96, 0.189, 1);

    public static final Color THE_UNWORTHY_FILL_COLOR =
            new Color((float) 231 / 255, (float) 200 / 255, (float) 100 / 255, 1);
    public static final Color THE_UNWORTHY_STROKE_COLOR =
            new Color(0.667, 0.96, 0.189, 1);

    public static final Color THE_HIGHLIGHTED_FILL_COLOR =
            new Color((float) 4 / 255, (float) 200 / 255, (float) 100 / 255, 1);
    public static final Color THE_HIGHLIGHTED_STROKE_COLOR =
            new Color(0.667, 0.96, 0.189, 0.25);

    public static final Color THE_OUTSTANDING_FILL_COLOR =
            new Color((float) 18 / 255, (float) 200 / 255, (float) 250 / 255, 0.8);
    public static final Color THE_OUTSTANDING_STROKE_COLOR =
            new Color(0.667, 0.96, 0.189, 0.25);


    // size
    public static final double MINIMUM_RECT_HEIGHT = 0.03D;
    public static final double MAXIMUM_RECT_HEIGHT = 0.97D;
    //status

    public static final int NORMAL_RECT_STATUS = 0;
    public static final int THE_CHOSEN_RECT_STATUS = 1;
    public static final int THE_TRUMP_RECT_STATUS = 2;
    public static final int THE_UNWORTHY_RECT_STATUS = 3;

    public static final int THE_HIGHLIGHTED_RECT_STATUS = 6;
    public static final int THE_OUTSTANDING_RECT_STATUS = 8;


    public static final int SOMEWHAT_SPECIAL_RECT_STATUS = -10;
    public static final int GATE_RECT_STATUS = 7;
    public static final int OBSTACLE_RECT_STATUS = 5;

    //Add more when have more status

    public static HashMap<Integer, List<Color>> COLORS_ARRAY = new HashMap<Integer, List<Color>>();

    static {
        COLORS_ARRAY.put(NORMAL_RECT_STATUS,
                Arrays.asList(NORMAL_FILL_COLOR, NORMAL_STROKE_COLOR));

        COLORS_ARRAY.put(SOMEWHAT_SPECIAL_RECT_STATUS,
                Arrays.asList(SOMEWHAT_SPECIAL_FILL_COLOR, SOMEWHAT_SPECIAL_STROKE_COLOR));

        COLORS_ARRAY.put(GATE_RECT_STATUS,
                Arrays.asList(GATE_FILL_COLOR, GATE_STROKE_COLOR));

        COLORS_ARRAY.put(OBSTACLE_RECT_STATUS,
                Arrays.asList(OBSTACLE_FILL_COLOR, OBSTACLE_STROKE_COLOR));

        COLORS_ARRAY.put(THE_CHOSEN_RECT_STATUS,
                Arrays.asList(THE_CHOSEN_FILL_COLOR, THE_CHOSEN_STROKE_COLOR));

        COLORS_ARRAY.put(THE_TRUMP_RECT_STATUS,
                Arrays.asList(THE_TRUMP_FILL_COLOR, THE_TRUMP_STROKE_COLOR));

        COLORS_ARRAY.put(THE_UNWORTHY_RECT_STATUS,
                Arrays.asList(THE_UNWORTHY_FILL_COLOR, THE_UNWORTHY_STROKE_COLOR));

        COLORS_ARRAY.put(THE_HIGHLIGHTED_RECT_STATUS,
                Arrays.asList(THE_HIGHLIGHTED_FILL_COLOR, THE_HIGHLIGHTED_STROKE_COLOR));

        COLORS_ARRAY.put(THE_OUTSTANDING_RECT_STATUS,
                Arrays.asList(THE_OUTSTANDING_FILL_COLOR, THE_OUTSTANDING_STROKE_COLOR));
    }

    private static final int VALUE_OF_FILL_COLOR = 0;
    private static final int VALUE_OF_STROKE_COLOR = 1;

    //Log parameters
    public static final int MAXIMUM_QUEUING_MESSAGE = 50;

    public static final int STANDARD_LOG_RATE = 1;
    public static final int MAXIMUM_LOG_RATE = 100;
    public static final int MINIMUM_LOG_RATE = 0;

    public static final float RATE_INCREASE_COEFFICIENT = 1.3F;
    public static final float RATE_DECAY_COEFFICIENT = 0.5F;

    public static final int SLEEP_TIME_PER_RATE = 10;


    public static Color getFill(int status) {
        try {
            return COLORS_ARRAY.get(status).get(VALUE_OF_FILL_COLOR);
        } catch (Exception e) {
            System.out.println("Can't get color");
        }
        return new Color(0, 0, 0, 0);
    }


    public static Color getStroke(int status) {
        try {
            return COLORS_ARRAY.get(status).get(VALUE_OF_STROKE_COLOR);
        } catch (Exception e) {
            System.out.println("Can't get color");
        }
        return new Color(0, 0, 0, 0);
    }
}

