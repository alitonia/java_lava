package utils;

import javafx.scene.paint.Color;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class consts{

    public static final int MAX_EXECUTIONERS = 4;
    //Thread Priority
    public static final int LOG_PRIORITY = 8;


    //Data properties
    public static final int NUMBER_OF_RECTANGLE = 20;


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
    public static final int NORMAL_RECT = 0;

    //Add more when have more status
    public static Color[][] COLORS_ARRAY = { {NORMAL_FILL_COLOR, NORMAL_STROKE_COLOR},
            {NORMAL_STROKE_COLOR, NORMAL_FILL_COLOR}
    };

    //Log parameters
    public static final int STANDARD_LOG_RATE = 1;
    public static final int MAXIMUM_LOG_RATE = 1000;
    public static final int MINIMUM_LOG_RATE = 0;

    public static final float RATE_INCREASE_COEFFICIENT = 1.3F;
    public static final float RATE_DECAY_COEFFICIENT = 0.5F;

    public static final int SLEEP_TIME_PER_RATE = 10;



    public static Color getFill(int status){
        try {
            return COLORS_ARRAY[status][0];
        } catch (Exception e){
            System.out.println("Can't get color");
        }
        return new Color(0,0,0,0);
    }

    public static Color getStroke(int status){
        try {
            return COLORS_ARRAY[status][1];
        } catch (Exception e){
            System.out.println("Can't get color");
        }
        return new Color(0,0,0,0);
    }
}

