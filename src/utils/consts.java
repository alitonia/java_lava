package utils;

import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class consts {

    //Thread
    public static final int MAX_EXECUTIONERS = 3; //?

    //Operating mode:
    public static final String SEQUENTIAL_SEARCH_MODE = "Sequential Search";
    public static final String BINARY_SEARCH_MODE = "Binary Search";
    public static final String A_STAR_PATH_FINDING_MODE = "A* Path-finding";


    //Data properties
    public static final int NUMBER_OF_RECTANGLE = 20;
    public static final int NUMBER_OF_RECTANGLE_X_AXIS = 30;
    public static final int NUMBER_OF_RECTANGLE_Y_AXIS = 20;
    public static final double HEIGHT_VARIANCE_COEFFICIENT = 1.3;
    public static final int MINIMUM_HISTOGRAM_TRAVERSING_DISTANCE = (int) (NUMBER_OF_RECTANGLE * 0.3);

    public static final boolean TARGET_LINE_TO_FRONT = true;

    //Play-speed
    public static final long SEQUENTIAL_SEARCH_DELAY_MILLISECOND = (int) 5000 / NUMBER_OF_RECTANGLE;
    public static final long BINARY_SEARCH_DELAY_MILLISECOND = SEQUENTIAL_SEARCH_DELAY_MILLISECOND * 2;
    public static final long A_STAR_DELAY_MILLISECOND = SEQUENTIAL_SEARCH_DELAY_MILLISECOND * 3;

    // size
    public static final double MINIMUM_RECT_HEIGHT = 0.03;
    public static final double MAXIMUM_RECT_HEIGHT = 0.97;

    public static final double PERCENT_RIGHT_PADDING_OF_A_STAR = 0.015;
    public static final double PERCENT_LEFT_PADDING_OF_A_STAR = 0.015;
    public static final double PERCENT_UP_PADDING_A_STAR = 0.03;
    public static final double PERCENT_DOWN_PADDING_A_STAR = 0.03;


    //color

    //Colorful Rect properties
    //normal
    public static final Color THE_NORMAL_FILL_COLOR =
            new Color((float) 255 / 255, (float) 154 / 255, (float) 118 / 255, 1);
    public static final Color THE_NORMAL_STROKE_COLOR =
            new Color((float) 255 / 255, (float) 203 / 255, (float) 203 / 255, 0.95);

    //focus
    public static final Color THE_FOCUSED_FILL_COLOR =
            new Color((float) 255 / 255, (float) 165 / 255, (float) 163 / 255, 1);
    public static final Color THE_FOCUSED_STROKE_COLOR =
            new Color((float) 11 / 255, (float) 199 / 255, (float) 207 / 255, 0.35);

    //choosing
    public static final Color THE_SPOTLIGHT_FILL_COLOR =
            new Color((float) 252 / 255, (float) 248 / 255, (float) 118 / 255, 1);
    public static final Color THE_SPOTLIGHT_STROKE_COLOR =
            new Color((float) 95 / 255, (float) 221 / 255, (float) 229 / 255, 0.8);

    //chosen
    public static final Color THE_SUCCESSFUL_FILL_COLOR =
            new Color((float) 95 / 255, (float) 221 / 255, (float) 229 / 255, 1);
    public static final Color THE_SUCCESSFUL_STROKE_COLOR =
            new Color((float) 252 / 255, (float) 248 / 255, (float) 118 / 255, 0.8);

    //not satisfy
    public static final Color THE_UNWORTHY_FILL_COLOR =
            new Color((float) 217 / 255, (float) 69 / 255, (float) 95 / 255, 1);
    public static final Color THE_UNWORTHY_STROKE_COLOR =
            new Color((float) 225 / 255, (float) 203 / 255, (float) 203 / 255, 0.95);


    //start and end point
    public static final Color THE_GATE_FILL_COLOR =
            new Color((float) 252 / 255, (float) 248 / 255, (float) 118 / 255, 1);
    public static final Color THE_GATE_STROKE_COLOR =
            new Color((float) 95 / 255, (float) 221 / 255, (float) 229 / 255, 0.5);


    //hindrance in map
    public static final Color THE_OBSTACLE_FILL_COLOR =
            new Color((float) 47 / 255, (float) 196 / 255, (float) 178 / 255, 1);
    public static final Color THE_OBSTACLE_STROKE_COLOR =
            new Color((float) 255 / 255, (float) 203 / 255, (float) 203 / 255, 0.95);


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
    //For rectangle color
    public static final int THE_NORMAL_RECT_STATUS = 0;
    public static final int THE_FOCUSED_RECT_STATUS = 1;

    public static final int THE_SPOTLIGHT_RECT_STATUS = 2;
    public static final int THE_SUCCESSFUL_RECT_STATUS = 3;
    public static final int THE_UNWORTHY_RECT_STATUS = 4;

    public static final int THE_GATE_RECT_STATUS = 5;
    public static final int THE_OBSTACLE_RECT_STATUS = 6;

    public static final int THE_HIGHLIGHTED_RECT_STATUS = 7;
    public static final int THE_OUTSTANDING_RECT_STATUS = 8;

    public static final int THE_DEFAULT_RECT_STATUS = THE_NORMAL_RECT_STATUS;


    //For initialize 2D map
    public static final int WALK_ABLE_SIGNAL = -1;
    public static final int BLOCKED_SIGNAL = -2;
    public static final int DESTINATION_SIGNAL = -3;
    public static final int SOURCE_SIGNAL = -4;



    //Add more when have more status
    public static HashMap<Integer, List<Color>> COLOR_MAP = new HashMap<Integer, List<Color>>();

    static {
        COLOR_MAP.put(THE_NORMAL_RECT_STATUS,
                Arrays.asList(THE_NORMAL_FILL_COLOR, THE_NORMAL_STROKE_COLOR));

        COLOR_MAP.put(THE_FOCUSED_RECT_STATUS,
                Arrays.asList(THE_FOCUSED_FILL_COLOR, THE_FOCUSED_STROKE_COLOR));

        COLOR_MAP.put(THE_SPOTLIGHT_RECT_STATUS,
                Arrays.asList(THE_SPOTLIGHT_FILL_COLOR, THE_SPOTLIGHT_STROKE_COLOR));

        COLOR_MAP.put(THE_SUCCESSFUL_RECT_STATUS,
                Arrays.asList(THE_SUCCESSFUL_FILL_COLOR, THE_SUCCESSFUL_STROKE_COLOR));

        COLOR_MAP.put(THE_UNWORTHY_RECT_STATUS,
                Arrays.asList(THE_UNWORTHY_FILL_COLOR, THE_UNWORTHY_STROKE_COLOR));

        COLOR_MAP.put(THE_GATE_RECT_STATUS,
                Arrays.asList(THE_GATE_FILL_COLOR, THE_GATE_STROKE_COLOR));

        COLOR_MAP.put(THE_OBSTACLE_RECT_STATUS,
                Arrays.asList(THE_OBSTACLE_FILL_COLOR, THE_OBSTACLE_STROKE_COLOR));


        COLOR_MAP.put(THE_HIGHLIGHTED_RECT_STATUS,
                Arrays.asList(THE_HIGHLIGHTED_FILL_COLOR, THE_HIGHLIGHTED_STROKE_COLOR));

        COLOR_MAP.put(THE_OUTSTANDING_RECT_STATUS,
                Arrays.asList(THE_OUTSTANDING_FILL_COLOR, THE_OUTSTANDING_STROKE_COLOR));

    }


    private static final int VALUE_OF_FILL_COLOR = 0;
    private static final int VALUE_OF_STROKE_COLOR = 1;

    public static Color getFill(int status) {
        try {
            return COLOR_MAP.get(status).get(VALUE_OF_FILL_COLOR);
        } catch (Exception e) {
            System.out.println("Can't get color");
        }
        return new Color(0, 0, 0, 0);
    }


    public static Color getStroke(int status) {
        try {
            return COLOR_MAP.get(status).get(VALUE_OF_STROKE_COLOR);
        } catch (Exception e) {
            System.out.println("Can't get color");
        }
        return new Color(0, 0, 0, 0);
    }


    public static long getDelay(String execution_Mode) {
        switch (execution_Mode) {
            case SEQUENTIAL_SEARCH_MODE:
                return SEQUENTIAL_SEARCH_DELAY_MILLISECOND;

            case BINARY_SEARCH_MODE:
                return BINARY_SEARCH_DELAY_MILLISECOND;

            case A_STAR_PATH_FINDING_MODE:
                return A_STAR_DELAY_MILLISECOND;
            default:
                return 1;
        }
    }
}

