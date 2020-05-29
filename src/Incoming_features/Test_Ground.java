package Incoming_features;

import utils.backend_logic.State;
import utils.backend_logic.history_Manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test_Ground {
    public static void main(String[] args) {
        Sorting_Utils sorting_utils = new Sorting_Utils();
        history_Manager history_manager = new history_Manager();

        List<Double> my_Values = Arrays.asList(1.0, 4.0, 2.0);
        List<State> origin = new ArrayList<>();

        for (int i = 0; i < my_Values.size(); i++) {
            origin.add(new State(i, 0, my_Values.get(i)));
        }

        history_manager.set_Origin_List(origin);
        sorting_utils.Bubble_Sort(my_Values, history_manager);

        history_manager.print();
        System.out.println(my_Values.toString());

    }
}
