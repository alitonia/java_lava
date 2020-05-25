package utils.backend_logic;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.consts.THE_NORMAL_RECT_STATUS;

public class Mini_testing {
    // Only keep for the shake of integration
    // TODO: Delete upon finishing A*
    public static void main(String[] args) {
        // TODO code application logic here
        history_Manager q = new history_Manager();

        List<Double> a = Arrays.asList(0D, 1D, 2D, 3D, 4D, 5D, 6D);
        List<State> l = new ArrayList<>();

        for (int i = 0; i < a.size(); i++) {
            l.add(new State(i, THE_NORMAL_RECT_STATUS));
        }
        SearchingUtils s = new SearchingUtils();
        q.setInternal_List(l);
        q.setOrigin_List(l);

//        q = SearchingUtils.binary_Search(6, a, 0, a.length - 1);
        s.binary_Search(6, a, q);
//        s.sequential_Search(6, a);
        q.print();
    }
}
