package utils.backend_logic;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.consts.THE_NORMAL_RECT_STATUS;

public class Mini_testing {
    public static void main(String[] args) {
        // TODO code application logic here
        my_Queue q = new my_Queue();

        List<Double> a = Arrays.asList(0D, 1D, 2D, 3D, 4D, 5D, 6D);
        List<State> l = new ArrayList<>();

        for (int i = 0; i < a.size(); i++) {
            l.add(new State(i, THE_NORMAL_RECT_STATUS));
        }
        SearchingUtils s = new SearchingUtils();
        my_Queue.setInternal_List(l);

//        q = SearchingUtils.Binary_Search(6, a, 0, a.length - 1);
        s.Binary_Search(6, a, 0, a.size() - 1);
//        s.Sequential_Search(6, a);
        my_Queue.print();
    }
}
