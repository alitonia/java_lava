package utils.backend_logic;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import java.util.ArrayList;
import java.util.List;

import static utils.consts.*;


public class A_Path_Finding {

    private Node start_Node;
    private Node end_Node;

    // Delete?
    private ArrayList<Node> block = new ArrayList<>();

    // in-progress queue
    private ArrayList<Node> OpenList = new ArrayList<>();

    // done?
    private ArrayList<Node> CloseList = new ArrayList<>();

    private ArrayList<ArrayList<Node>> Map_2D;
    //Some thing like this:

    // 1  2  3  4  5
    // 6  7  8  9  10
    // 11 12 13 14 15

    public A_Path_Finding() {
    }

    public A_Path_Finding(int row, int col) {
        start_Node = new Node(7, 0);
        end_Node = new Node(0, 0);
        Map_2D = new ArrayList<>();

        for (int i = 0; i < row; i++) {
            ArrayList<Node> temp = new ArrayList<>();
            for (int j = 0; j < col; j++) {
                temp.add(new Node(i, j));
            }
            Map_2D.add(temp);
        }
    }

    private void init(int number_of_Rectangle_in_X_axis, int number_of_Rectangle_in_Y_axis, List<Node> my_Nodes) {
        ArrayList<Node> temp;
        int index;
        Node current_Node;
        double state;
        this.Map_2D = new ArrayList<>();

        // De-flatten my_Nodes --> 2D Map
        for (int i = 0; i < number_of_Rectangle_in_Y_axis; i++) {
            temp = new ArrayList<>();

            for (int j = 0; j < number_of_Rectangle_in_X_axis; j++) {
                index = i * number_of_Rectangle_in_X_axis + j;
                current_Node = my_Nodes.get(index);

                state = current_Node.get_F();                    // Un-wanted implementation :< . Up for refactoring

                // Procedures for special nodes
                if (state == BLOCKED_SIGNAL) {
                    block.add(current_Node);                    // another un-wanted implementation. Up for change

                } else if (state == END_SIGNAL) {
                    this.end_Node = new Node(current_Node);

                } else if (state == START_SIGNAL) {
                    this.start_Node = new Node(current_Node);
                }

                temp.add(current_Node);
            }

            Map_2D.add(temp);
        }
    }


    public void find_Path(int row_Size, int column_Size, List<Node> my_Nodes, history_Manager q) {
        //Initialize
        init(row_Size, column_Size, my_Nodes);
        System.out.println(Map_2D.toString());

        // check src and dest is valid or not
        if (!is_Valid(start_Node) || !is_Valid(end_Node)) {
            return;
        }

        // check src and dest is blocked or not
        if (is_Blocked(get_Start_node()) || is_Blocked(get_End_node())) {
            return;
        }


        // check src and dest equal so we are already at the destination
        // this might not happens though
        if (is_Destination(get_Start_node())) {
            System.out.println("We are already at the destination!!!");
            return;
        }


        // Initialise all parameters for each node in map
        for (int i = 0; i < get_Map().size(); i++) {
            for (int j = 0; j < get_Map().get(0).size(); j++) {
                get_Map().get(i).get(j).set_F(Double.MAX_VALUE);
                get_Map().get(i).get(j).set_H(Double.MAX_VALUE);
                get_Map().get(i).get(j).set_G(Double.MAX_VALUE);
                Node parent = new Node(-1, -1);
                get_Map().get(i).get(j).set_Parent(parent);
                get_Map().get(i).get(j).set_Parent(parent);
            }
        }

        // Initialize value for start node
        start_Node.set_F(0.0);
        start_Node.set_G(0.0);
        start_Node.set_H(0.0);

        Node start_node_parent = new Node();
        start_node_parent.set_X(start_Node.get_X());
        start_node_parent.set_Y(start_Node.get_Y());

        start_Node.set_Parent(start_node_parent);

        // Add to the open list the start node
        get_Open_List().add(get_Start_node());

        boolean found_Dest = false;

        Node x = new Node();

        while (get_Open_List().size() != 0) {
            Node temp = new Node();

            // pop the node have lowest value f
            temp = get_Open_List().get(0);

            get_Open_List().remove(0);

            get_Close_List().add(temp);

            int i = temp.get_X();

            int j = temp.get_Y();

            Node parent = new Node(i, j);

            double new_G, new_H, new_F;
            // Check the next node at position [i-1,j]
            if (is_Valid(i - 1, j)) {
                x = get_Map().get(i - 1).get(j);
                if (is_Destination(x)) {
                    x.set_Parent(parent);
                    // trace path(print path)
                    System.out.println("The destination is found");
                    this.trace_Path();
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
                    this.trace_Path();
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
                    this.trace_Path();
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
                    this.trace_Path();
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
                    this.trace_Path();
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
                    this.trace_Path();
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
                    this.trace_Path();
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
                    this.trace_Path();
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
            return;
        }
    }


    public void trace_Path() {
        int row = get_End_node().get_X();
        int col = get_End_node().get_Y();
        ArrayList<Node> Path = new ArrayList<Node>();
        while (!(Map_2D.get(row).get(col).getParent().get_X() == -1 && Map_2D.get(row).get(col).getParent().get_Y() == -1)) {
            Node X = new Node(row, col);
            ;
            Path.add(X);
            int temp_row = get_Map().get(row).get(col).getParent().get_X();
            int temp_col = get_Map().get(row).get(col).getParent().get_Y();
            row = temp_row;
            col = temp_col;
        }
        Path.add(get_Start_node());
        for (int i = Path.size() - 1; i >= 0; i--) {
            if (i != 0) {
                System.out.printf("(%d %d)--->", Path.get(i).get_X(), Path.get(i).get_Y());
            } else {
                System.out.printf("(%d %d)", Path.get(i).get_X(), Path.get(i).get_Y());
            }
        }
    }


    public double calculate_H_Value(Node x) {
        return ((double) Math.sqrt(Math.pow(x.get_X() - get_End_node().get_X(), 2) + Math.pow(x.get_Y() - get_End_node().get_Y(), 2)));
    }

    public double calculate_G_Value(Node x, Node y) {
        return ((double) Math.sqrt(Math.pow(x.get_X() - y.get_X(), 2) + Math.pow(x.get_Y() - y.get_Y(), 2)));
    }


    public boolean is_Valid(int x, int y) {
        return x >= 0 && x < get_Map().size() && y >= 0 && y < get_Map().get(0).size();
    }

    public boolean is_Valid(Node x) {
        return x.get_X() >= 0 && x.get_X() < get_Map().size() && x.get_Y() >= 0 && x.get_Y() < get_Map().get(0).size();
    }

    public boolean is_Destination(Node x) {
        return x.check_overlap(get_End_node());
    }

    public boolean is_Blocked(Node x) {
        for (int i = 0; i < get_Block().size(); i++) {
            if (x.check_overlap(get_Block().get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean is_Closed(Node x) {
        for (int i = 0; i < get_Close_List().size(); i++) {
            if (x.check_overlap(get_Close_List().get(i))) {
                return true;
            }
        }
        return false;
    }


    @Getter
    public Node get_Start_node() {
        return start_Node;
    }

    public Node get_End_node() {
        return end_Node;
    }

    public ArrayList<Node> get_Block() {
        return block;
    }

    public ArrayList<Node> get_Open_List() {
        return OpenList;
    }

    public ArrayList<Node> get_Close_List() {
        return CloseList;
    }

    public ArrayList<ArrayList<Node>> get_Map() {
        return Map_2D;
    }


    @Setter
    public void set_Start_node(Node start_node) {
        this.start_Node = start_node;
    }

    public void set_End_node(Node end_node) {
        this.end_Node = end_node;
    }

    public void set_Block(ArrayList<Node> block) {
        this.block = block;
    }

    public void set_Open_List(ArrayList<Node> openList) {
        OpenList = openList;
    }

    public void set_Close_List(ArrayList<Node> closeList) {
        CloseList = closeList;
    }

    public void set_Map(ArrayList<ArrayList<Node>> map) {
        Map_2D = map;
    }
}