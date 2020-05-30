package utils.front_end_logic;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import components.Controller;
import utils.Log;
import utils.Random_Color;
import utils.backend_logic.Node;
import utils.backend_logic.State;

import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static utils.consts.*;


public class Array_Controller {

    private Controller controller;
    private ObservableList<Colorful_Rectangle> colorful_rectangles;
    private int length;
    // Frequently used parameter
    double board_Width;
    double board_Height;
    double board_X;
    double board_Y;
    Log my_Log = new Log();

    public Array_Controller(Controller c) {
        this.controller = c;
        Log log = new Log();
        log.print("Array_controller created.");

    }

    // Run procedure that can't be done in Constructor
    // due to some "unique features" of javaFX
    private void init() {
        Pane visual_Board = controller.get_Visual_Board();

        // Frequently used parameter
        this.board_Width = visual_Board.getWidth();
        this.board_Height = visual_Board.getHeight();
        this.board_X = visual_Board.getLayoutX();
        this.board_Y = visual_Board.getLayoutY();

        controller.clean_Board();
        colorful_rectangles = FXCollections.observableArrayList();
    }

    public void make_Histogram(int number_of_Rectangles) {
        // Create histogram with number of elements = length
        // uniform width, length randomly assigned between min and max length
        // X and Y so that the whole histogram looks like a histogram
        // Default status: NORMAL_RECT_STATUS

        my_Log.print("Making histogram");
        init();
        this.length = number_of_Rectangles;
        double rectangle_Width = board_Width / length;

        for (int i = 0; i < number_of_Rectangles; i++) {
            Colorful_Rectangle rectangle = new Colorful_Rectangle();

            // Get random height within visual_Board range,
            // then round over so target line can easily be distinguished
            double rectangle_Height = Math.floor((
                    ThreadLocalRandom.current().nextDouble(MINIMUM_RECT_HEIGHT, MAXIMUM_RECT_HEIGHT)
                            * board_Height)
                    / HEIGHT_VARIANCE_COEFFICIENT) * HEIGHT_VARIANCE_COEFFICIENT;

            rectangle.setHeight(rectangle_Height);
            rectangle.setWidth(rectangle_Width);

            rectangle.setX(board_X + i * rectangle_Width);
            rectangle.setY(board_Y + (board_Height - rectangle_Height));
            rectangle.setStatus(THE_NORMAL_RECT_STATUS);

            this.colorful_rectangles.add(rectangle);
        }
        my_Log.print("Done");
        controller.paint_Board(colorful_rectangles);
    }


    public void make_Ordered_Histogram(int number_of_Rectangles) {
        // make_Histogram histogram, then sort it ---> ordered histogram
        make_Histogram(number_of_Rectangles);
        my_Log.print("Sorting histogram");
        sort(this.colorful_rectangles, 0, colorful_rectangles.size() - 1);
        my_Log.print("Done");
    }


    public void make_Map(int num_of_X_axis_Rectangle, int num_of_Y_axis_Rectangle) {
        // Make a map to demonstrate A* Path-finding algorithm
        // Map line in visual_Board, has size X_axis_in_rectangle x Y_axis_in_rectangle
        // Top and bottom has 2 portals that needs to connect with algorithm
        // Randomly generate obstacles on the map

        my_Log.print("Making 2D map");
        init();
        // Map 2D list into 1D
        this.length = num_of_X_axis_Rectangle * num_of_Y_axis_Rectangle;

        // Map generation part
        double rectangle_Width =
                ((1 - PERCENT_LEFT_PADDING_OF_A_STAR - PERCENT_RIGHT_PADDING_OF_A_STAR) * board_Width)
                        / num_of_X_axis_Rectangle;

        double rectangle_Height =
                ((1 - PERCENT_UP_PADDING_A_STAR - PERCENT_DOWN_PADDING_A_STAR) * board_Height)
                        / num_of_Y_axis_Rectangle;

        for (int i = 0; i < length; i++) {
            Colorful_Rectangle rectangle = new Colorful_Rectangle();

            rectangle.setWidth(rectangle_Width);
            rectangle.setHeight(rectangle_Height);

            int column = i % num_of_X_axis_Rectangle;
            int row = i / num_of_X_axis_Rectangle;
            rectangle.setX(
                    board_X + board_Width * PERCENT_LEFT_PADDING_OF_A_STAR
                            + column * rectangle_Width);

            rectangle.setY(
                    board_Y + board_Height * PERCENT_UP_PADDING_A_STAR +
                            row * rectangle_Height);

            // Make gate at (0x0) and (length_X x length_Y)
            if (i == 0 || i == length - 1) {
                rectangle.setStatus(THE_GATE_RECT_STATUS);
            } else {
                // Randomize obstacles with percentage 1/3
                int status = Random_Color.get_Random(
                        new Random_Color(THE_NORMAL_RECT_STATUS, 2.0 / 3.0),
                        new Random_Color(THE_OBSTACLE_RECT_STATUS, 1.0 / 3.0)
                );
                rectangle.setStatus(status);
            }

            colorful_rectangles.add(rectangle);
        }
        my_Log.print("Done");
        controller.paint_Board(colorful_rectangles);
    }


    // swap histogram elements
    public void swap(int index_1, int index_2) {
        Pane visual_Board = controller.get_Visual_Board();

        // Swap coordinate of index_1 and index_2 elements
        Colorful_Rectangle first_rectangle = colorful_rectangles.get(index_1);
        Colorful_Rectangle second_rectangle = colorful_rectangles.get(index_2);

        double first_X = first_rectangle.getX();

        first_rectangle.setX(second_rectangle.getX());
        first_rectangle.setY(visual_Board.getLayoutY() +
                (visual_Board.getHeight() - first_rectangle.getHeight()));

        second_rectangle.setX(first_X);
        second_rectangle.setY(visual_Board.getLayoutY() +
                (visual_Board.getHeight() - second_rectangle.getHeight()));

        // Do fictional swap in List
        // due to usage of index in finding position in Controller
        Collections.swap(colorful_rectangles, index_1, index_2);
    }


    public void sort(List<Colorful_Rectangle> rectangles, int start, int end) {
        // Quicksort
        // but altered to not mess up histogram display

        if (start < end) {
            int pivot = partition(rectangles, start, end);

            // Recursively sort on 2 sides of pivot
            sort(rectangles, start, pivot - 1);
            sort(rectangles, pivot + 1, end);
        }
    }


    private int partition(List<Colorful_Rectangle> rectangles, int start, int end) {
        // This version choose end index as pivot
        double pivot = rectangles.get(end).getHeight();

        int i = (start - 1); // index of smaller element
        for (int j = start; j < end; j++) {
            // If current element is smaller than the pivot
            if (rectangles.get(j).getHeight() < pivot) {
                i += 1;
                swap(i, j);
            }
        }
        // swap pivot and last index
        swap(i + 1, end);
        return i + 1;
    }


    @Getter
    public int getLength() {
        return length;
    }

    public List<Colorful_Rectangle> get_Colorful_Rectangles() {
        return colorful_rectangles;
    }

    public List<State> get_List_State_Format() {
        List<State> l = new ArrayList<>();
        for (int i = 0; i < colorful_rectangles.size(); i++) {
            l.add(
                    new State(i,
                            colorful_rectangles.get(i).getStatus(),
                            colorful_rectangles.get(i).getHeight()));
        }
        return l;
    }

    public List<Double> get_List_Double_Format() {
        List<Double> l = new ArrayList<>();
        for (int i = 0; i < colorful_rectangles.size(); i++) {
            l.add(colorful_rectangles.get(i).getHeight());
        }
        return l;
    }


    public List<Node> get_List_Node_Format() {
        List<Node> my_Node = new ArrayList<>();
        Node new_Node;
        int X_position;
        int Y_position;
        int status;

        for (int i = 0; i < colorful_rectangles.size(); i++) {
            X_position = i % NUMBER_OF_RECTANGLE_X_AXIS;
            Y_position = i / NUMBER_OF_RECTANGLE_X_AXIS;
            new_Node = new Node(X_position, Y_position);

            status = colorful_rectangles.get(i).getStatus();

            //Get appropriate f values
            if (i == 0) {
                new_Node.set_F(START_SIGNAL);
            } else if (i == colorful_rectangles.size() - 1) {
                new_Node.set_F(END_SIGNAL);
            } else if (status == THE_OBSTACLE_RECT_STATUS) {
                new_Node.set_F(BLOCKED_SIGNAL);
            } else {
                new_Node.set_F(WALK_ABLE_SIGNAL);
            }
            my_Node.add(new_Node);
        }

        return my_Node;
    }

    @Setter
    public void setColorful_rectangle(int index, int status) {
        colorful_rectangles.get(index).setStatus(status);
    }

    //Change status of 1 colorful rectangle
    public void setStatus(int index, int status) {
        Colorful_Rectangle rectangle = colorful_rectangles.get(index);
        rectangle.setStatus(status);
    }
}
