package A_Star_Lab;


import utils.backend_logic.Node;

import java.util.ArrayList;

public class APathFinding {
    private Node start_node;

    private Node end_node;

    private ArrayList<Node> Block = new ArrayList<>();

    private ArrayList<Node> OpenList = new ArrayList<>();

    private ArrayList<Node> CloseList = new ArrayList<>();

    private ArrayList<ArrayList<Node>> Map;

    public APathFinding(int X_limit, int Y_limit) {
        start_node = new Node(0, 0);
        end_node = new Node(7, 0);
        Map = new ArrayList<ArrayList<Node>>(X_limit);

        for (int i = 0; i < X_limit; i++) {
            ArrayList<Node> temp = new ArrayList<Node>(Y_limit);
            for (int j = 0; j < Y_limit; j++) {
                temp.add(new Node(i, j));
            }
            Map.add(temp);
        }

    }

    public Node getStart_node() {
        return start_node;
    }

    public void setStart_node(Node start_node) {
        this.start_node = start_node;
    }

    public Node getEnd_node() {
        return end_node;
    }

    public void setEnd_node(Node end_node) {
        this.end_node = end_node;
    }

    public ArrayList<Node> getBlock() {
        return Block;
    }

    public void setBlock(ArrayList<Node> Block) {
        this.Block = Block;
    }

    public ArrayList<Node> getOpenList() {
        return OpenList;
    }

    public void setOpenList(ArrayList<Node> OpenList) {
        this.OpenList = OpenList;
    }

    public ArrayList<Node> getCloseList() {
        return CloseList;
    }

    public void setCloseList(ArrayList<Node> CloseList) {
        this.CloseList = CloseList;
    }

    public ArrayList<ArrayList<Node>> getMap() {
        return Map;
    }

    public void setMap(ArrayList<ArrayList<Node>> Map) {
        this.Map = Map;
    }

    public boolean isValid(int x, int y) {
        return x >= 0 && x < getMap().size() && y >= 0 && y < getMap().get(0).size();
    }

    public boolean isValid(Node x) {
        return x.get_X() >= 0 && x.get_X() < getMap().size() && x.get_Y() >= 0 && x.get_Y() < getMap().get(0).size();
    }

    public boolean isDestination(Node x) {
        return x.check_Overlap(getEnd_node());
    }

    public boolean isBlocked(Node x) {
        for (int i = 0; i < getBlock().size(); i++) {
            if (x.check_Overlap(getBlock().get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean isClosed(Node x) {
        for (int i = 0; i < getCloseList().size(); i++) {
            if (x.check_Overlap(getCloseList().get(i))) {
                return true;
            }
        }
        return false;
    }

    public double calculate_H_value(Node x) {
        return ((double) Math.sqrt(Math.pow(x.get_X() - getEnd_node().get_X(), 2) + Math.pow(x.get_Y() - getEnd_node().get_Y(), 2)));
    }

    public double calculate_G_value(Node x, Node y) {
        return ((double) Math.sqrt(Math.pow(x.get_X() - y.get_X(), 2) + Math.pow(x.get_Y() - y.get_Y(), 2)));
    }

    public void Trace_Path() {
        int row = getEnd_node().get_X();
        int col = getEnd_node().get_Y();
        ArrayList<Node> Path = new ArrayList<Node>();
        while (!(Map.get(row).get(col).get_Parent().get_X() == -1 && Map.get(row).get(col).get_Parent().get_Y() == -1)) {
            Node X = new Node(row, col);
            ;
            Path.add(X);
            int temp_row = getMap().get(row).get(col).get_Parent().get_X();
            int temp_col = getMap().get(row).get(col).get_Parent().get_Y();
            row = temp_row;
            col = temp_col;
        }
        Path.add(getStart_node());
        for (int i = Path.size() - 1; i >= 0; i--) {
            if (i != 0) {
                System.out.printf("(%d %d)--->", Path.get(i).get_X(), Path.get(i).get_Y());
            } else {
                System.out.printf("(%d %d)", Path.get(i).get_X(), Path.get(i).get_Y());
            }
        }
    }

    public void find_Path() {
        System.out.println(Map.toString());
        // check src and dest is valid or not
        if (!isValid(start_node) || !isValid(end_node)) {
            return;
        }

        // check src and dest is blocked or not
        if (isBlocked(getStart_node()) || isBlocked(getEnd_node())) {
            return;
        }

        // check src and dest equal so we are already at the destination
        if (isDestination(getStart_node())) {
            System.out.println("We are already at the destination!!!");
            return;
        }

        // Initialise all parameters for each node in map
        for (int i = 0; i < getMap().size(); i++) {
            for (int j = 0; j < getMap().get(0).size(); j++) {
                getMap().get(i).get(j).set_F(Double.MAX_VALUE);
                getMap().get(i).get(j).set_H(Double.MAX_VALUE);
                getMap().get(i).get(j).set_G(Double.MAX_VALUE);
                Node parent = new Node(-1, -1);
                getMap().get(i).get(j).set_Parent(parent);
                getMap().get(i).get(j).set_Parent(parent);
            }
        }

        // Initialize value for start node
        start_node.set_F(0.0);
        start_node.set_G(0.0);
        start_node.set_H(0.0);

        Node start_node_parent = new Node();
        start_node_parent.set_X(start_node.get_X());
        start_node_parent.set_Y(start_node.get_Y());

        start_node.set_Parent(start_node_parent);

        // Add to the open list the start node
        getOpenList().add(getStart_node());

        boolean found_dest = false;

        Node x = new Node();

        while (getOpenList().size() != 0) {
            Node temp = new Node();

            // pop the node have lowest value f
            temp = getOpenList().get(0);

            getOpenList().remove(0);

            getCloseList().add(temp);

            int i = temp.get_X();

            int j = temp.get_Y();

            Node Parent = new Node(i, j);

            double NewG, NewH, NewF;
            // Check the next node at position [i-1,j]
            if (isValid(i - 1, j)) {
                x = getMap().get(i - 1).get(j);
                if (isDestination(x)) {
                    x.set_Parent(Parent);
                    // trace path(print path)
                    System.out.println("The destination is found");
                    this.Trace_Path();
                    found_dest = true;
                    return;
                } else {
                    if (!isClosed(x) && !isBlocked(x)) {
                        NewG = temp.get_G() + calculate_G_value(x, temp);
                        NewH = calculate_H_value(x);
                        NewF = NewG + NewH;
                        if (x.get_F() == Double.MAX_VALUE || x.get_F() > NewF) {
                            x.set_F(NewF);
                            getOpenList().add(x);

                            getMap().get(i - 1).get(j).set_G(NewG);
                            getMap().get(i - 1).get(j).set_H(NewH);

                            getMap().get(i - 1).get(j).set_Parent(Parent);
                            getMap().get(i - 1).get(j).set_Parent(Parent);

                        }
                    }
                }
            }
            // Check the next node at position [i+1,j]
            if (isValid(i + 1, j)) {
                x = getMap().get(i + 1).get(j);
                if (isDestination(x)) {
                    x.set_Parent(Parent);
                    // trace path(print path)
                    System.out.println("The destination is found");
                    this.Trace_Path();
                    found_dest = true;
                    return;
                } else {
                    if (!isClosed(x) && !isBlocked(x)) {
                        NewG = temp.get_G() + calculate_G_value(x, temp);
                        NewH = calculate_H_value(x);
                        NewF = NewG + NewH;
                        if (x.get_F() == Double.MAX_VALUE || x.get_F() > NewF) {
                            x.set_F(NewF);
                            getOpenList().add(x);

                            getMap().get(i + 1).get(j).set_G(NewG);
                            getMap().get(i + 1).get(j).set_H(NewH);
                            getMap().get(i + 1).get(j).set_Parent(Parent);
                            getMap().get(i + 1).get(j).set_Parent(Parent);
                        }
                    }
                }
            }
            //Check the node at position [i,j-1]
            if (isValid(i, j - 1)) {
                x = getMap().get(i).get(j - 1);
                if (isDestination(x)) {
                    x.set_Parent(Parent);
                    // trace path(print path)
                    System.out.println("The destination is found");
                    this.Trace_Path();
                    found_dest = true;
                    return;
                } else {
                    if (!isClosed(x) && !isBlocked(x)) {
                        NewG = temp.get_G() + calculate_G_value(x, temp);
                        NewH = calculate_H_value(x);
                        NewF = NewG + NewH;
                        if (x.get_F() == Double.MAX_VALUE || x.get_F() > NewF) {
                            x.set_F(NewF);
                            getOpenList().add(x);

                            getMap().get(i).get(j - 1).set_G(NewG);
                            getMap().get(i).get(j - 1).set_H(NewH);
                            getMap().get(i).get(j - 1).set_Parent(Parent);
                            getMap().get(i).get(j - 1).set_Parent(Parent);
                        }
                    }
                }
            }
            //Check the node at position [i,j+1]
            if (isValid(i, j + 1)) {
                x = getMap().get(i).get(j + 1);
                if (isDestination(x)) {
                    x.set_Parent(Parent);
                    // trace path(print path)
                    System.out.println("The destination is found");
                    this.Trace_Path();
                    found_dest = true;
                    return;
                } else {
                    if (!isClosed(x) && !isBlocked(x)) {
                        NewG = temp.get_G() + calculate_G_value(x, temp);
                        NewH = calculate_H_value(x);
                        NewF = NewG + NewH;
                        if (x.get_F() == Double.MAX_VALUE || x.get_F() > NewF) {
                            x.set_F(NewF);
                            getOpenList().add(x);

                            getMap().get(i).get(j + 1).set_G(NewG);
                            getMap().get(i).get(j + 1).set_H(NewH);
                            getMap().get(i).get(j + 1).set_Parent(Parent);
                            getMap().get(i).get(j + 1).set_Parent(Parent);
                        }
                    }
                }
            }
            //Check the node at position [i+1,j+1]
            if (isValid(i + 1, j + 1)) {
                x = getMap().get(i + 1).get(j + 1);
                if (isDestination(x)) {
                    x.set_Parent(Parent);
                    // trace path(print path)
                    System.out.println("The destination is found");
                    this.Trace_Path();
                    found_dest = true;
                    return;
                } else {
                    if (!isClosed(x) && !isBlocked(x)) {
                        NewG = temp.get_G() + calculate_G_value(x, temp);
                        NewH = calculate_H_value(x);
                        NewF = NewG + NewH;
                        if (x.get_F() == Double.MAX_VALUE || x.get_F() > NewF) {
                            x.set_F(NewF);
                            getOpenList().add(x);

                            getMap().get(i + 1).get(j + 1).set_G(NewG);
                            getMap().get(i + 1).get(j + 1).set_H(NewH);
                            getMap().get(i + 1).get(j + 1).set_Parent(Parent);
                            getMap().get(i + 1).get(j + 1).set_Parent(Parent);
                        }
                    }
                }
            }
            //Check the node at position [i+1,j-1]
            if (isValid(i + 1, j - 1)) {
                x = getMap().get(i + 1).get(j - 1);
                if (isDestination(x)) {
                    x.set_Parent(Parent);
                    // trace path(print path)
                    System.out.println("The destination is found");
                    this.Trace_Path();
                    found_dest = true;
                    return;
                } else {
                    if (!isClosed(x) && !isBlocked(x)) {
                        NewG = temp.get_G() + calculate_G_value(x, temp);
                        NewH = calculate_H_value(x);
                        NewF = NewG + NewH;
                        if (x.get_F() == Double.MAX_VALUE || x.get_F() > NewF) {
                            x.set_F(NewF);
                            getOpenList().add(x);

                            getMap().get(i + 1).get(j - 1).set_G(NewG);
                            getMap().get(i + 1).get(j - 1).set_H(NewH);
                            getMap().get(i + 1).get(j - 1).set_Parent(Parent);
                            getMap().get(i + 1).get(j - 1).set_Parent(Parent);
                        }
                    }
                }
            }
            //Check the node at position [i-1,j+1]
            if (isValid(i - 1, j + 1)) {
                x = getMap().get(i - 1).get(j + 1);
                if (isDestination(x)) {
                    x.set_Parent(Parent);
                    // trace path(print path)
                    System.out.println("The destination is found");
                    this.Trace_Path();
                    found_dest = true;
                    return;
                } else {
                    if (!isClosed(x) && !isBlocked(x)) {
                        NewG = temp.get_G() + calculate_G_value(x, temp);
                        NewH = calculate_H_value(x);
                        NewF = NewG + NewH;
                        if (x.get_F() == Double.MAX_VALUE || x.get_F() > NewF) {
                            x.set_F(NewF);
                            getOpenList().add(x);

                            getMap().get(i - 1).get(j + 1).set_G(NewG);
                            getMap().get(i - 1).get(j + 1).set_H(NewH);
                            getMap().get(i - 1).get(j + 1).set_Parent(Parent);
                            getMap().get(i - 1).get(j + 1).set_Parent(Parent);
                        }
                    }
                }
            }
            //Check the node at position [i-1,j-1]
            if (isValid(i - 1, j - 1)) {
                x = getMap().get(i - 1).get(j - 1);
                if (isDestination(x)) {
                    x.set_Parent(Parent);
                    // trace path(print path)
                    System.out.println("The destination is found");
                    this.Trace_Path();
                    found_dest = true;
                    return;
                } else {
                    if (!isClosed(x) && !isBlocked(x)) {
                        NewG = temp.get_G() + calculate_G_value(x, temp);
                        NewH = calculate_H_value(x);
                        NewF = NewG + NewH;
                        if (x.get_F() == Double.MAX_VALUE || x.get_F() > NewF) {
                            x.set_F(NewF);
                            getOpenList().add(x);

                            getMap().get(i - 1).get(j - 1).set_G(NewG);
                            getMap().get(i - 1).get(j - 1).set_H(NewH);
                            getMap().get(i - 1).get(j - 1).set_Parent(Parent);
                            getMap().get(i - 1).get(j - 1).set_Parent(Parent);
                        }
                    }
                }
            }
        }
        // if not finding the path from start_node to end_node
        if (!found_dest) {
            return;
        }
    }
}

