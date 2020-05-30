package utils.backend_logic;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import utils.Translator;
import utils.constants;

import java.util.ArrayList;
import java.util.List;

import static utils.constants.*;


public class A_Path_Finding {

    private Graph_Node start_Graph_Node;
    private Graph_Node end_Graph_Node;

    private ArrayList<Graph_Node> block = new ArrayList<>();

    // in-progress queue
    private ArrayList<Graph_Node> open_List = new ArrayList<>();

    // visited nodes
    private ArrayList<Graph_Node> close_List = new ArrayList<>();

    private ArrayList<ArrayList<Graph_Node>> map_2D;

    private int number_of_Rectangle_In_X_Axis;
    private int number_of_Rectangle_In_Y_Axis;

    //Some thing like this:

    // 1  2  3  4  5
    // 6  7  8  9  10
    // 11 12 13 14 15


    public A_Path_Finding() {
    }


    private void init(List<Graph_Node> my_Graph_Nodes) {
        //Initialize parameters for find_Path

        ArrayList<Graph_Node> temp;
        int index;
        Graph_Node current_Graph_Node;
        double state;
        this.map_2D = new ArrayList<>();

        // construct map
        // note that the map is sliced in horizontal axis, which is absolutely weird
        for (int i = 0; i < number_of_Rectangle_In_X_Axis; i++) {
            map_2D.add(new ArrayList<>());
        }


        // De-flatten my_Graph_Nodes --> 2D Map
        for (int i = 0; i < my_Graph_Nodes.size(); i++) {
            current_Graph_Node = my_Graph_Nodes.get(i);
            state = current_Graph_Node.get_F();       // Un-wanted implementation :< . Up for refactoring


            if (state == BLOCKED_SIGNAL) {
                block.add(current_Graph_Node);                    // another un-wanted implementation. Up for change

            } else if (state == END_SIGNAL) {
                this.end_Graph_Node = new Graph_Node(current_Graph_Node);

            } else if (state == START_SIGNAL) {
                this.start_Graph_Node = new Graph_Node(current_Graph_Node);
            }

            int X_idex = i % number_of_Rectangle_In_X_Axis;
            map_2D.get(X_idex).add(current_Graph_Node);
        }
    }


    public void find_Path(int number_of_Rectangle_In_X_axis, int number_of_Rectangle_In_Y_axis,
                          List<Graph_Node> my_Graph_Nodes, History_Manager q) {
        clear();
        //Initialize
        this.number_of_Rectangle_In_X_Axis = number_of_Rectangle_In_X_axis;
        this.number_of_Rectangle_In_Y_Axis = number_of_Rectangle_In_Y_axis;

        init(my_Graph_Nodes);

        List<State_Blob> current_Step;

        System.out.println(map_2D.toString());

        // check src and dest is valid or not
        if (!is_Valid(start_Graph_Node) || !is_Valid(end_Graph_Node)) {
            return;
        }

        // check src and dest is blocked or not
        if (is_Blocked(get_Start_Node()) || is_Blocked(get_End_Node())) {
            return;
        }


        // check src and dest equal so we are already at the destination
        // this might not happens though
        if (is_Destination(get_Start_Node())) {
            System.out.println("We are already at the destination!!!");
            return;
        }


        // Initialise all parameters for each node in map
        for (int i = 0; i < get_Map().size(); i++) {
            for (int j = 0; j < get_Map().get(0).size(); j++) {
                get_Map().get(i).get(j).set_F(Double.MAX_VALUE);
                get_Map().get(i).get(j).set_H(Double.MAX_VALUE);
                get_Map().get(i).get(j).set_G(Double.MAX_VALUE);
                Graph_Node parent = new Graph_Node(-1, -1);
                get_Map().get(i).get(j).set_Parent(parent);
                get_Map().get(i).get(j).set_Parent(parent);
            }
        }

        // Initialize value for start node
        start_Graph_Node.set_F(0.0);
        start_Graph_Node.set_G(0.0);
        start_Graph_Node.set_H(0.0);

        Graph_Node start_Graph_Node_Parent = new Graph_Node();
        start_Graph_Node_Parent.set_X(start_Graph_Node.get_X());
        start_Graph_Node_Parent.set_Y(start_Graph_Node.get_Y());

        start_Graph_Node.set_Parent(start_Graph_Node_Parent);

        // Add to the open list the start node
        get_Open_List().add(get_Start_Node());


        boolean found_Dest = false;

        Graph_Node x = new Graph_Node();

        while (get_Open_List().size() != 0) {
            Graph_Node temp = new Graph_Node();

            // pop the node have lowest value f
            temp = get_Open_List().get(0);


            System.out.println("Selecting " + temp.toString());

            current_Step = new ArrayList<>();
            current_Step.add(new State_Blob(Translator.flatten(temp.get_X(), temp.get_Y(), number_of_Rectangle_In_X_axis),
                    THE_VISITING_RECT_STATUS, NO_REPAINT_HEIGHT_SIGNAL));

            q.add(current_Step);


            get_Open_List().remove(0);

            get_Close_List().add(temp);


            System.out.println("Visited " + temp.toString() + "\n");

            current_Step = new ArrayList<>();
            // Highlight if node visited
            // except for gates, which are already highlighted
            if (temp != start_Graph_Node && temp != end_Graph_Node) {
                current_Step.add(new State_Blob(
                        Translator.flatten(temp.get_X(), temp.get_Y(), number_of_Rectangle_In_X_axis),
                        constants.THE_VISITED_RECT_STATUS, NO_REPAINT_HEIGHT_SIGNAL));
            } else {
                current_Step.add(new State_Blob(
                        Translator.flatten(temp.get_X(), temp.get_Y(), number_of_Rectangle_In_X_axis),
                        THE_GATE_RECT_STATUS, NO_REPAINT_HEIGHT_SIGNAL));
            }
            q.add(current_Step);


            int i = temp.get_X();

            int j = temp.get_Y();

            Graph_Node parent = new Graph_Node(i, j);

            double new_G, new_H, new_F;
            // Check the next node at position [i-1,j]
            if (is_Valid(i - 1, j)) {
                x = get_Map().get(i - 1).get(j);
                if (is_Destination(x)) {
                    x.set_Parent(parent);
                    // trace path(print path)
                    System.out.println("The destination is found");
                    this.trace_Path(q);
                    found_Dest = true;
                    return;
                } else {
                    if (!is_Closed(x) && !is_Blocked(x)) {
                        new_G = temp.get_G() + calculate_G_Value(x, temp);
                        new_H = calculate_H_Value(x);
                        new_F = new_G + new_H;
                        if (x.get_F() == Double.MAX_VALUE || x.get_F() > new_F) {
                            x.set_F(new_F);
                            get_Open_List().add(x);

                            get_Map().get(i - 1).get(j).set_G(new_G);
                            get_Map().get(i - 1).get(j).set_H(new_H);

                            get_Map().get(i - 1).get(j).set_Parent(parent);
                            get_Map().get(i - 1).get(j).set_Parent(parent);

                        }
                    }
                }
            }
            // Check the next node at position [i+1,j]
            if (is_Valid(i + 1, j)) {
                x = get_Map().get(i + 1).get(j);
                if (is_Destination(x)) {
                    x.set_Parent(parent);
                    // trace path(print path)
                    System.out.println("The destination is found");
                    this.trace_Path(q);
                    found_Dest = true;
                    return;
                } else {
                    if (!is_Closed(x) && !is_Blocked(x)) {
                        new_G = temp.get_G() + calculate_G_Value(x, temp);
                        new_H = calculate_H_Value(x);
                        new_F = new_G + new_H;
                        if (x.get_F() == Double.MAX_VALUE || x.get_F() > new_F) {
                            x.set_F(new_F);
                            get_Open_List().add(x);

                            get_Map().get(i + 1).get(j).set_G(new_G);
                            get_Map().get(i + 1).get(j).set_H(new_H);
                            get_Map().get(i + 1).get(j).set_Parent(parent);
                            get_Map().get(i + 1).get(j).set_Parent(parent);
                        }
                    }
                }
            }
            //Check the node at position [i,j-1]
            if (is_Valid(i, j - 1)) {
                x = get_Map().get(i).get(j - 1);
                if (is_Destination(x)) {
                    x.set_Parent(parent);
                    // trace path(print path)
                    System.out.println("The destination is found");
                    this.trace_Path(q);
                    found_Dest = true;
                    return;
                } else {
                    if (!is_Closed(x) && !is_Blocked(x)) {
                        new_G = temp.get_G() + calculate_G_Value(x, temp);
                        new_H = calculate_H_Value(x);
                        new_F = new_G + new_H;
                        if (x.get_F() == Double.MAX_VALUE || x.get_F() > new_F) {
                            x.set_F(new_F);
                            get_Open_List().add(x);

                            get_Map().get(i).get(j - 1).set_G(new_G);
                            get_Map().get(i).get(j - 1).set_H(new_H);
                            get_Map().get(i).get(j - 1).set_Parent(parent);
                            get_Map().get(i).get(j - 1).set_Parent(parent);
                        }
                    }
                }
            }
            //Check the node at position [i,j+1]
            if (is_Valid(i, j + 1)) {
                x = get_Map().get(i).get(j + 1);
                if (is_Destination(x)) {
                    x.set_Parent(parent);
                    // trace path(print path)
                    System.out.println("The destination is found");
                    this.trace_Path(q);
                    found_Dest = true;
                    return;
                } else {
                    if (!is_Closed(x) && !is_Blocked(x)) {
                        new_G = temp.get_G() + calculate_G_Value(x, temp);
                        new_H = calculate_H_Value(x);
                        new_F = new_G + new_H;
                        if (x.get_F() == Double.MAX_VALUE || x.get_F() > new_F) {
                            x.set_F(new_F);
                            get_Open_List().add(x);

                            get_Map().get(i).get(j + 1).set_G(new_G);
                            get_Map().get(i).get(j + 1).set_H(new_H);
                            get_Map().get(i).get(j + 1).set_Parent(parent);
                            get_Map().get(i).get(j + 1).set_Parent(parent);
                        }
                    }
                }
            }
            //Check the node at position [i+1,j+1]
            if (is_Valid(i + 1, j + 1)) {
                x = get_Map().get(i + 1).get(j + 1);
                if (is_Destination(x)) {
                    x.set_Parent(parent);
                    // trace path(print path)
                    System.out.println("The destination is found");
                    this.trace_Path(q);
                    found_Dest = true;
                    return;
                } else {
                    if (!is_Closed(x) && !is_Blocked(x)) {
                        new_G = temp.get_G() + calculate_G_Value(x, temp);
                        new_H = calculate_H_Value(x);
                        new_F = new_G + new_H;
                        if (x.get_F() == Double.MAX_VALUE || x.get_F() > new_F) {
                            x.set_F(new_F);
                            get_Open_List().add(x);

                            get_Map().get(i + 1).get(j + 1).set_G(new_G);
                            get_Map().get(i + 1).get(j + 1).set_H(new_H);
                            get_Map().get(i + 1).get(j + 1).set_Parent(parent);
                            get_Map().get(i + 1).get(j + 1).set_Parent(parent);
                        }
                    }
                }
            }
            //Check the node at position [i+1,j-1]
            if (is_Valid(i + 1, j - 1)) {
                x = get_Map().get(i + 1).get(j - 1);
                if (is_Destination(x)) {
                    x.set_Parent(parent);
                    // trace path(print path)
                    System.out.println("The destination is found");
                    this.trace_Path(q);
                    found_Dest = true;
                    return;
                } else {
                    if (!is_Closed(x) && !is_Blocked(x)) {
                        new_G = temp.get_G() + calculate_G_Value(x, temp);
                        new_H = calculate_H_Value(x);
                        new_F = new_G + new_H;
                        if (x.get_F() == Double.MAX_VALUE || x.get_F() > new_F) {
                            x.set_F(new_F);
                            get_Open_List().add(x);

                            get_Map().get(i + 1).get(j - 1).set_G(new_G);
                            get_Map().get(i + 1).get(j - 1).set_H(new_H);
                            get_Map().get(i + 1).get(j - 1).set_Parent(parent);
                            get_Map().get(i + 1).get(j - 1).set_Parent(parent);
                        }
                    }
                }
            }
            //Check the node at position [i-1,j+1]
            if (is_Valid(i - 1, j + 1)) {
                x = get_Map().get(i - 1).get(j + 1);
                if (is_Destination(x)) {
                    x.set_Parent(parent);
                    // trace path(print path)
                    System.out.println("The destination is found");
                    this.trace_Path(q);
                    found_Dest = true;
                    return;
                } else {
                    if (!is_Closed(x) && !is_Blocked(x)) {
                        new_G = temp.get_G() + calculate_G_Value(x, temp);
                        new_H = calculate_H_Value(x);
                        new_F = new_G + new_H;
                        if (x.get_F() == Double.MAX_VALUE || x.get_F() > new_F) {
                            x.set_F(new_F);
                            get_Open_List().add(x);

                            get_Map().get(i - 1).get(j + 1).set_G(new_G);
                            get_Map().get(i - 1).get(j + 1).set_H(new_H);
                            get_Map().get(i - 1).get(j + 1).set_Parent(parent);
                            get_Map().get(i - 1).get(j + 1).set_Parent(parent);
                        }
                    }
                }
            }
            //Check the node at position [i-1,j-1]
            if (is_Valid(i - 1, j - 1)) {
                x = get_Map().get(i - 1).get(j - 1);
                if (is_Destination(x)) {
                    x.set_Parent(parent);
                    // trace path(print path)
                    System.out.println("The destination is found");
                    this.trace_Path(q);
                    found_Dest = true;
                    return;
                } else {
                    if (!is_Closed(x) && !is_Blocked(x)) {
                        new_G = temp.get_G() + calculate_G_Value(x, temp);
                        new_H = calculate_H_Value(x);
                        new_F = new_G + new_H;
                        if (x.get_F() == Double.MAX_VALUE || x.get_F() > new_F) {
                            x.set_F(new_F);
                            get_Open_List().add(x);

                            get_Map().get(i - 1).get(j - 1).set_G(new_G);
                            get_Map().get(i - 1).get(j - 1).set_H(new_H);
                            get_Map().get(i - 1).get(j - 1).set_Parent(parent);
                            get_Map().get(i - 1).get(j - 1).set_Parent(parent);
                        }
                    }
                }
            }
        }
        // if not finding the path from start_node to end_node
        if (!found_Dest) {
            System.out.println("Not found!");
            System.out.println(close_List.toString());

            current_Step = new ArrayList<>();
            for (Graph_Node n :
                    close_List) {
                current_Step.add(new State_Blob(
                        Translator.flatten(n.get_X(), n.get_Y(), number_of_Rectangle_In_X_axis),
                        THE_OUTSTANDING_RECT_STATUS, NO_REPAINT_HEIGHT_SIGNAL));
            }
            q.add(current_Step);

            return;
        }
        System.out.println("End");
    }


    public void trace_Path(History_Manager q) {
        int row = get_End_Node().get_X();
        int col = get_End_Node().get_Y();
        ArrayList<Graph_Node> Path = new ArrayList<>();
        while (!(map_2D.get(row).get(col).get_Parent().get_X() == -1 && map_2D.get(row).get(col).get_Parent().get_Y() == -1)) {
            Graph_Node X = new Graph_Node(row, col);
            ;
            Path.add(X);
            int temp_row = get_Map().get(row).get(col).get_Parent().get_X();
            int temp_col = get_Map().get(row).get(col).get_Parent().get_Y();
            row = temp_row;
            col = temp_col;
        }
        Path.add(get_Start_Node());
        System.out.println();


        List<State_Blob> current_Step = new ArrayList<>();

        for (int i = Path.size() - 1; i >= 0; i--) {
            if (i != 0) {
                System.out.printf("(%d %d)--->", Path.get(i).get_X(), Path.get(i).get_Y());
            } else {
                System.out.printf("(%d %d)", Path.get(i).get_X(), Path.get(i).get_Y());
            }
            current_Step.add(new State_Blob(
                    Translator.flatten(Path.get(i).get_X(), Path.get(i).get_Y(), number_of_Rectangle_In_X_Axis),
                    THE_GATE_RECT_STATUS, NO_REPAINT_HEIGHT_SIGNAL));
        }
        q.add(current_Step);

        System.out.println();
        System.out.println(close_List.toString());

    }


    public double calculate_H_Value(Graph_Node x) {
        return Math.sqrt(Math.pow(x.get_X() - get_End_Node().get_X(), 2) + Math.pow(x.get_Y() - get_End_Node().get_Y(), 2));
    }

    public double calculate_G_Value(Graph_Node x, Graph_Node y) {
        return Math.sqrt(Math.pow(x.get_X() - y.get_X(), 2) + Math.pow(x.get_Y() - y.get_Y(), 2));
    }


    public boolean is_Valid(int x, int y) {
        return x >= 0 && x < get_Map().size() && y >= 0 && y < get_Map().get(0).size();
    }

    public boolean is_Valid(Graph_Node x) {
        return x.get_X() >= 0 && x.get_X() < get_Map().size() && x.get_Y() >= 0 && x.get_Y() < get_Map().get(0).size();
    }

    public boolean is_Destination(Graph_Node x) {
        return x.check_Overlap(get_End_Node());
    }

    public boolean is_Blocked(Graph_Node x) {
        for (int i = 0; i < get_Block().size(); i++) {
            if (x.check_Overlap(get_Block().get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean is_Closed(Graph_Node x) {
        for (int i = 0; i < get_Close_List().size(); i++) {
            if (x.check_Overlap(get_Close_List().get(i))) {
                return true;
            }
        }
        return false;
    }


    private void clear() {
        // Make (almost) eveything new again
        this.block = new ArrayList<>();
        this.close_List = new ArrayList<>();
        this.open_List = new ArrayList<>();
        this.map_2D = new ArrayList<>();
    }


    @Getter
    public Graph_Node get_Start_Node() {
        return start_Graph_Node;
    }

    public Graph_Node get_End_Node() {
        return end_Graph_Node;
    }

    public ArrayList<Graph_Node> get_Block() {
        return block;
    }

    public void set_Block(ArrayList<Graph_Node> block) {
        this.block = block;
    }

    public ArrayList<Graph_Node> get_Open_List() {
        return open_List;
    }

    public void set_Open_List(ArrayList<Graph_Node> openList) {
        open_List = openList;
    }

    public ArrayList<Graph_Node> get_Close_List() {
        return close_List;
    }

    public void set_Close_List(ArrayList<Graph_Node> closeList) {
        close_List = closeList;
    }

    public ArrayList<ArrayList<Graph_Node>> get_Map() {
        return map_2D;
    }

    public void set_Map(ArrayList<ArrayList<Graph_Node>> map_2D) {
        this.map_2D = map_2D;
    }

    @Setter
    public void set_Start_node(Graph_Node start_Graph_Node) {
        this.start_Graph_Node = start_Graph_Node;
    }

    public void set_End_node(Graph_Node end_Graph_Node) {
        this.end_Graph_Node = end_Graph_Node;
    }
}