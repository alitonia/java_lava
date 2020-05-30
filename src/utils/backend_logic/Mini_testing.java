package utils.backend_logic;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.Random_Color;
import utils.front_end_logic.Colorful_Rectangle;

import java.util.ArrayList;
import java.util.List;

import static utils.consts.*;

public class Mini_testing {
    // Only keep for the shake of integration


    // TODO: Delete upon finishing A*
    public static void main(String[] args) {
        // TODO code application logic here
        history_Manager q = new history_Manager();
        A_Path_Finding generator = new A_Path_Finding();

        int number_of_rectangle_in_X = 5;
        int number_of_rectangle_in_Y = 7;

        //Initialize test
        Beard b = new Beard();
        b.make_Map(number_of_rectangle_in_X, number_of_rectangle_in_Y);


        //This is the Map passed into generator
        List<Node> given_Nodes = b.get_List_Node_Format(number_of_rectangle_in_X, number_of_rectangle_in_Y);
        q.set_Origin_List(b.get_List_State_Format());

        //Validate
        System.out.println("Node values in current list: ");
        for (int i = 0; i < given_Nodes.size(); i++) {
            System.out.print(i + ": " + given_Nodes.get(i).toString() + " ");
            if (given_Nodes.get(i).get_F() == WALK_ABLE_SIGNAL) {
                System.out.println("Walkable");
            }
            if (given_Nodes.get(i).get_F() == BLOCKED_SIGNAL) {
                System.out.println("Block");
            }
            if (given_Nodes.get(i).get_F() == START_SIGNAL) {
                System.out.println("Source");
            }
            if (given_Nodes.get(i).get_F() == END_SIGNAL) {
                System.out.println("Destination");
            }
            if ((i + 1) % number_of_rectangle_in_X == 0) {
                System.out.println();
            }
        }

        // Insert your generator here:
        generator.find_Path(number_of_rectangle_in_X, number_of_rectangle_in_Y,
                given_Nodes, q);


        //Print nodes to verify
        q.print();

    }
}

class Beard {
    private ObservableList<Colorful_Rectangle> colorful_rectangles;
    private int length;

    private void init() {
        colorful_rectangles = FXCollections.observableArrayList();
    }

    public void make_Map(int num_of_X_axis_Rectangle, int num_of_Y_axis_Rectangle) {
        // Make a map to demonstrate A* Path-finding algorithm
        // Map line in visual_Board, has size X_axis_in_rectangle x Y_axis_in_rectangle
        // Top and bottom has 2 portals that needs to connect with algorithm
        // Randomly generate obstacles on the map

        init();
        // Map 2D list into 1D
        length = num_of_X_axis_Rectangle * num_of_Y_axis_Rectangle;

        // Map generation part
        double rectangle_Width = 5;
        double rectangle_Height = 5;

        for (int i = 0; i < length; i++) {
            Colorful_Rectangle rectangle = new Colorful_Rectangle();

            rectangle.setWidth(rectangle_Width);
            rectangle.setHeight(rectangle_Height);

            int column = i % num_of_X_axis_Rectangle;
            int row = i / num_of_X_axis_Rectangle;

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
    }


    public List<Node> get_List_Node_Format(int num_of_X_axis_Rectangle, int num_of_Y_axis_Rectangle) {
        List<Node> my_Node = new ArrayList<>();
        Node new_Node;
        int X_position;
        int Y_position;
        int status;

        for (int i = 0; i < colorful_rectangles.size(); i++) {
            X_position = i % num_of_X_axis_Rectangle;
            Y_position = i / num_of_X_axis_Rectangle;
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
}