package A_Star_Lab;

import utils.backend_logic.Node;

public class test_Center {

    public static void main(String[] args) {
        APathFinding obj = new APathFinding(8, 5);

        Node x = new Node(6, 1);
        Node y = new Node(5, 1);
        Node z = new Node(4, 1);
        Node w = new Node(2, 0);
        Node t = new Node(2, 1);
        Node a = new Node(1, 0);
        Node b = new Node(1, 1);
        obj.getBlock().add(x);
        obj.getBlock().add(y);
        obj.getBlock().add(z);
        obj.getBlock().add(w);
        obj.getBlock().add(t);
        obj.getBlock().add(a);
        obj.getBlock().add(b);

        obj.find_Path();
    }

}

