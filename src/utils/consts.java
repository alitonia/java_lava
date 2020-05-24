package utils;

import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class consts {

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
    public static final int NUMBER_OF_RECTANGLE = 30;
    public static final int NUMBER_OF_RECTANGLE_X_AXIS = 30;
    public static final int NUMBER_OF_RECTANGLE_Y_AXIS = 20;
    public static final double HEIGHT_VARIANCE_COEFFICIENT = 1.3;
    public static final int MINIMUM_TRAVERSING_DISTANCE = (int) (NUMBER_OF_RECTANGLE * 0.3);

    //Play-speed
    public static final long DELAY_MILIS_PER_RUN = 5000 / NUMBER_OF_RECTANGLE;


    // size
    public static final double MINIMUM_RECT_HEIGHT = 0.03D;
    public static final double MAXIMUM_RECT_HEIGHT = 0.97D;

    public static final double PERCENT_RIGHT_PADDING_A_STAR = 0.015D;
    public static final double PERCENT_LEFT_PADDING_A_STAR = 0.015D;
    public static final double PERCENT_UP_PADDING_A_STAR = 0.03D;
    public static final double PERCENT_DOWN_PADDING_A_STAR = 0.03D;


    //color
    //Other's color

    //Colorful Rect properties

    public static final Color NORMAL_FILL_COLOR =
            new Color((float) 255 / 255, (float) 154 / 255, (float) 118 / 255, 1);
    //            new Color(0.158, 0.89, 0.96, 0.75);
    public static final Color NORMAL_STROKE_COLOR =
            new Color((float) 255 / 255, (float) 203 / 255, (float) 203 / 255, 0.95);

    public static final Color FOCUSED_FILL_COLOR =
            new Color((float) 255 / 255, (float) 165 / 255, (float) 163 / 255, 1);
    //            new Color(0.17, 0.85, 0.459, 1);
    public static final Color FOCUSED_STROKE_COLOR =
            new Color((float) 11 / 255, (float) 199 / 255, (float) 207 / 255, 0.35);

    public static final Color GATE_FILL_COLOR =
            new Color((float) 252 / 255, (float) 248 / 255, (float) 118 / 255, 1);
    public static final Color GATE_STROKE_COLOR =
            new Color((float) 95 / 255, (float) 221 / 255, (float) 229 / 255, 0.5);

    public static final Color OBSTACLE_FILL_COLOR =
            new Color((float) 47 / 255, (float) 196 / 255, (float) 178 / 255, 1);
    public static final Color OBSTACLE_STROKE_COLOR =
            new Color((float) 255 / 255, (float) 203 / 255, (float) 203 / 255, 0.95);

    public static final Color THE_SPOTLIGHT_FILL_COLOR =
            new Color((float) 252 / 255, (float) 248 / 255, (float) 118 / 255, 1);
    //            new Color(0.667, 0.96, 0.189, 1);
    public static final Color THE_SPOTLIGHT_STROKE_COLOR =
            new Color((float) 95 / 255, (float) 221 / 255, (float) 229 / 255, 0.8);

    public static final Color THE_SUCCESSFUL_FILL_COLOR =
            new Color((float) 95 / 255, (float) 221 / 255, (float) 229 / 255, 1);
    public static final Color THE_SUCCESSFUL_STROKE_COLOR =
            new Color((float) 252 / 255, (float) 248 / 255, (float) 118 / 255, 0.8);

    public static final Color THE_UNWORTHY_FILL_COLOR =
            new Color((float) 217 / 255, (float) 69 / 255, (float) 95 / 255, 1);
    public static final Color THE_UNWORTHY_STROKE_COLOR =
            new Color((float) 225 / 255, (float) 203 / 255, (float) 203 / 255, 0.95);


    //These aren't used
    public static final Color THE_HIGHLIGHTED_FILL_COLOR =
            new Color((float) 4 / 255, (float) 200 / 255, (float) 100 / 255, 1);
    public static final Color THE_HIGHLIGHTED_STROKE_COLOR =
            new Color(0.667, 0.96, 0.189, 0.25);

    public static final Color THE_OUTSTANDING_FILL_COLOR =
            new Color((float) 18 / 255, (float) 200 / 255, (float) 250 / 255, 0.8);
    public static final Color THE_OUTSTANDING_STROKE_COLOR =
            new Color(0.667, 0.96, 0.189, 0.25);



    //status
    public static final int NORMAL_RECT_STATUS = 0;
    public static final int FOCUSED_RECT_STATUS = 1;

    public static final int THE_SPOTLIGHT_RECT_STATUS = 2;
    public static final int THE_SUCCESSFUL_RECT_STATUS = 3;
    public static final int THE_UNWORTHY_RECT_STATUS = 4;

    public static final int GATE_RECT_STATUS = 5;
    public static final int OBSTACLE_RECT_STATUS = 6;

    public static final int THE_HIGHLIGHTED_RECT_STATUS = 7;
    public static final int THE_OUTSTANDING_RECT_STATUS = 8;


    //Add more when have more status
    public static HashMap<Integer, List<Color>> COLORS_ARRAY = new HashMap<Integer, List<Color>>();

    static {
        COLORS_ARRAY.put(NORMAL_RECT_STATUS,
                Arrays.asList(NORMAL_FILL_COLOR, NORMAL_STROKE_COLOR));

        COLORS_ARRAY.put(FOCUSED_RECT_STATUS,
                Arrays.asList(FOCUSED_FILL_COLOR, FOCUSED_STROKE_COLOR));

        COLORS_ARRAY.put(THE_SPOTLIGHT_RECT_STATUS,
                Arrays.asList(THE_SPOTLIGHT_FILL_COLOR, THE_SPOTLIGHT_STROKE_COLOR));

        COLORS_ARRAY.put(THE_SUCCESSFUL_RECT_STATUS,
                Arrays.asList(THE_SUCCESSFUL_FILL_COLOR, THE_SUCCESSFUL_STROKE_COLOR));

        COLORS_ARRAY.put(THE_UNWORTHY_RECT_STATUS,
                Arrays.asList(THE_UNWORTHY_FILL_COLOR, THE_UNWORTHY_STROKE_COLOR));

        COLORS_ARRAY.put(GATE_RECT_STATUS,
                Arrays.asList(GATE_FILL_COLOR, GATE_STROKE_COLOR));

        COLORS_ARRAY.put(OBSTACLE_RECT_STATUS,
                Arrays.asList(OBSTACLE_FILL_COLOR, OBSTACLE_STROKE_COLOR));


        COLORS_ARRAY.put(THE_HIGHLIGHTED_RECT_STATUS,
                Arrays.asList(THE_HIGHLIGHTED_FILL_COLOR, THE_HIGHLIGHTED_STROKE_COLOR));

        COLORS_ARRAY.put(THE_OUTSTANDING_RECT_STATUS,
                Arrays.asList(THE_OUTSTANDING_FILL_COLOR, THE_OUTSTANDING_STROKE_COLOR));

    }

    //Log parameters
    public static final int MAXIMUM_QUEUING_MESSAGE = 50;

    public static final int STANDARD_LOG_RATE = 1;
    public static final int MAXIMUM_LOG_RATE = 100;
    public static final int MINIMUM_LOG_RATE = 0;

    public static final float RATE_INCREASE_COEFFICIENT = 1.3F;
    public static final float RATE_DECAY_COEFFICIENT = 0.5F;

    public static final int SLEEP_TIME_PER_RATE = 10;

    private static final int VALUE_OF_FILL_COLOR = 0;
    private static final int VALUE_OF_STROKE_COLOR = 1;

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

    public static long getDelay(int execution_Mode) {
        if (execution_Mode == SEQUENTIAL_MODE) {
            return DELAY_MILIS_PER_RUN;
        } else if (execution_Mode == BINARY_MODE) {
            return DELAY_MILIS_PER_RUN * 3;
        } else if (execution_Mode == A_STAR_MODE) {
            return DELAY_MILIS_PER_RUN * 3;
        } else return 1;
    }
}

