package Incoming_features;

import components.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import utils.Log;
import utils.backend_logic.Node;
import utils.backend_logic.State;
import utils.front_end_logic.Colorful_Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static utils.consts.*;

public class Small_Array_Controller {
    private ObservableList<Colorful_Rectangle> colorful_rectangles;
    private int length;
    // Frequently used parameter
    double board_Width;
    double board_Height;
    double board_X;
    double board_Y;


    // Run procedure that can't be done in Constructor
    // due to some "unique features" of javaFX
    private void init() {

        // Frequently used parameter
        this.board_Width = 200;
        this.board_Height = 100;
        this.board_X = 0;
        this.board_Y = 0;

        colorful_rectangles = FXCollections.observableArrayList();
    }

    public void make_Histogram(int number_of_Rectangles) {
        // Create histogram with number of elements = length
        // uniform width, length randomly assigned between min and max length
        // X and Y so that the whole histogram looks like a histogram
        // Default status: NORMAL_RECT_STATUS

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
                new_Node.setF(SOURCE_SIGNAL);
            } else if (i == colorful_rectangles.size() - 1) {
                new_Node.setF(DESTINATION_SIGNAL);
            } else if (status == THE_OBSTACLE_RECT_STATUS) {
                new_Node.setF(BLOCKED_SIGNAL);
            } else {
                new_Node.setF(WALK_ABLE_SIGNAL);
            }
            my_Node.add(new_Node);
        }

        return my_Node;
    }
}
