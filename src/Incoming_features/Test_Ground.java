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

        List<Double> my_Values = Arrays.asList(1.0, 4.0, 2.0);
        List<State_Blob> origin = new ArrayList<>();

        for (int i = 0; i < my_Values.size(); i++) {
            origin.add(new State_Blob(i, 0, my_Values.get(i)));
        }

        history_manager.set_Origin_List(origin);
        sorting_utils.Bubble_Sort(my_Values, history_manager);

        history_manager.print();
        System.out.println(my_Values.toString());

    }
}