package Incoming_features;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.backend_logic.Graph_Node;
import utils.backend_logic.State_Blob;
import utils.front_end_logic.Colorful_Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static utils.constants.*;

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

    public List<State_Blob> get_List_State_Format() {
        List<State_Blob> l = new ArrayList<>();
        for (int i = 0; i < colorful_rectangles.size(); i++) {
            l.add(
                    new State_Blob(i,
                            colorful_rectangles.get(i).getStatus(),
                            colorful_rectangles.get(i).getHeight()));
        }
        return l;
    }

    public List<Double> get_List_Double_Format() {
        List<Double> l = new ArrayList<>();
        for (Colorful_Rectangle colorful_rectangle : colorful_rectangles) {
            l.add(colorful_rectangle.getHeight());
        }
        return l;
    }


    public List<Graph_Node> get_List_Node_Format() {
        List<Graph_Node> my_Graph_Node = new ArrayList<>();
        Graph_Node new_Graph_Node;
        int X_position;
        int Y_position;
        int status;

        for (int i = 0; i < colorful_rectangles.size(); i++) {
            X_position = i % NUMBER_OF_RECTANGLE_X_AXIS;
            Y_position = i / NUMBER_OF_RECTANGLE_X_AXIS;
            new_Graph_Node = new Graph_Node(X_position, Y_position);

            status = colorful_rectangles.get(i).getStatus();

            //Get appropriate f values
            if (i == 0) {
                new_Graph_Node.set_F(START_SIGNAL);
            } else if (i == colorful_rectangles.size() - 1) {
                new_Graph_Node.set_F(END_SIGNAL);
            } else if (status == THE_OBSTACLE_RECT_STATUS) {
                new_Graph_Node.set_F(BLOCKED_SIGNAL);
            } else {
                new_Graph_Node.set_F(WALK_ABLE_SIGNAL);
            }
            my_Graph_Node.add(new_Graph_Node);
        }

        return my_Graph_Node;
    }
}
