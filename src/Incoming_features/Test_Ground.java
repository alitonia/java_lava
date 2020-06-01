package Incoming_features;

import utils.backend_logic.History_Manager;
import utils.backend_logic.State_Blob;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test_Ground {
    public static void main(String[] args) {
        Sorting_Utils sorting_utils = new Sorting_Utils();
        History_Manager history_manager = new History_Manager();

        List<Double> my_Values = Arrays.asList(1.0, -5.0, 6.0, 4.0, 9.0, 1.0, 0.0, 11.0);
        List<State_Blob> origin = new ArrayList<>();

        for (int i = 0; i < my_Values.size(); i++) {
            origin.add(new State_Blob(i, 0, my_Values.get(i)));
        }

        history_manager.set_Origin_List(origin);
        sorting_utils.merge_Sort(my_Values,
                0, my_Values.size() - 1,
                history_manager);

        history_manager.print();
        System.out.println(my_Values.toString());

    }
}
