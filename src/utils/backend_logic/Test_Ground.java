package utils.backend_logic;

import java.util.ArrayList;
import java.util.List;

public class Test_Ground {
    public static void main(String[] args) {
        A_Path_Finding obj = new A_Path_Finding();
        // 8 x 5
        Node x = new Node(6, 1);
        Node y = new Node(5, 1);
        Node z = new Node(4, 1);
        Node w = new Node(2, 0);
        Node t = new Node(2, 1);
        Node a = new Node(1, 0);
        Node b = new Node(1, 1);


        obj.get_Block().add(x);
        obj.get_Block().add(y);
        obj.get_Block().add(z);
        obj.get_Block().add(w);
        obj.get_Block().add(t);
        obj.get_Block().add(a);
        obj.get_Block().add(b);

//        obj.find_Path();
    }
}
