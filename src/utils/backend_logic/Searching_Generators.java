package utils.backend_logic;

import utils.Log;

import java.util.ArrayList;
import java.util.List;

import static utils.constants.*;

//TODO: Name change on finishing A_Star

public class Searching_Generators {

    private int status;
    private List<State_Blob> change_List;
    Log my_Log = new Log();

    public void sequential_Search(double search_value, List<Double> a,
                                  History_Manager history_Queue) {
        //Push coloring process of sequential search to history Queue

        my_Log.print("Generating sequential search steps");
        for (int i = 0; i < a.size(); i++) {
            change_List = new ArrayList<>();
            status = THE_SPOTLIGHT_RECT_STATUS;
            change_List.add(new State_Blob(i, status, a.get(i)));

            history_Queue.add(change_List);

            //Found correct value?
            if (a.get(i) == search_value) {
                change_List = new ArrayList<>();
                status = THE_SUCCESSFUL_RECT_STATUS;
                change_List.add(new State_Blob(i, status, a.get(i)));

                history_Queue.add(change_List);
                break;
            }
            //Not correct value?
            else {
                change_List = new ArrayList<>();
                status = THE_UNWORTHY_RECT_STATUS;
                change_List.add(new State_Blob(i, status, a.get(i)));

                history_Queue.add(change_List);
            }
        }
    my_Log.print("Done");
    }

    public void binary_Search(double search_value, List<Double> a,
                              History_Manager history_Queue) {
        //Push coloring process of binary search to history Queue
        my_Log.print("Generating binary search steps");

        int start = 0;
        int end = a.size() - 1;


        //Range of focus ( also better coloring)
        //Highlight the interacting part of histogram

        change_List = new ArrayList<>();
        status = THE_FOCUSED_RECT_STATUS;
        for (int i = start; i < end + 1; i++) {
            change_List.add(new State_Blob(i, status, a.get(i)));
        }
        history_Queue.add(change_List);


        while (start <= end) {
            int mid = start + (end - start) / 2; // same as (l+r)/2 but avoid overflow;
            //??!?

            //Highlight middle index
            change_List = new ArrayList<>();
            status = THE_SPOTLIGHT_RECT_STATUS;
            change_List.add(new State_Blob(mid, status, a.get(mid)));

            history_Queue.add(change_List);

            if (search_value == a.get(mid)) {
                //When Found the value, highlight it, stop
                change_List = new ArrayList<>();
                status = THE_SUCCESSFUL_RECT_STATUS;
                change_List.add(new State_Blob(mid, status, a.get(mid)));

                history_Queue.add(change_List);
                break;

            } else {
                change_List = new ArrayList<>();
                status = THE_UNWORTHY_RECT_STATUS;
                change_List.add(new State_Blob(mid, status, a.get(mid)));

                history_Queue.add(change_List);

                //Mark the wrong path
                if (search_value < a.get(mid)) {
                    change_List = new ArrayList<>();
                    status = THE_UNWORTHY_RECT_STATUS;

                    for (int i = mid + 1; i < end + 1; i++) {
                        change_List.add(new State_Blob(i, status, a.get(i)));
                    }
                    history_Queue.add(change_List);
                    end = mid - 1;

                } else {
                    change_List = new ArrayList<>();
                    status = THE_UNWORTHY_RECT_STATUS;

                    for (int i = start; i < mid; i++) {
                        change_List.add(new State_Blob(i, status, a.get(i)));
                    }
                    history_Queue.add(change_List);
                    start = mid + 1;
                }
            }
        }
        my_Log.print("Done");
    }
}