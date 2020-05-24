package utils.backend_logic;

import utils.consts;

import java.util.ArrayList;
import java.util.List;

import static utils.consts.*;

public class SearchingUtils {

    private my_Queue q = new my_Queue();
    private int status;
    private int index;
    private State state;
    private List<State> change_List;

    public void Sequential_Search(double search_value, List<Double> a) {

        for (int i = 0; i < a.size(); i++) {
            status = THE_CHOSEN_RECT_STATUS;
            index = i;
            state = new State(index, status);

            change_List = new ArrayList<>();
            change_List.add(state);
            q.add(change_List);

            //Found correct value?
            if (a.get(i) == search_value) {
                index = i;
                status = THE_TRUMP_RECT_STATUS;
                state = new State(index, status);

                change_List = new ArrayList<>();
                change_List.add(state);
                q.add(change_List);
                break;
            }
            //Not correct value?
            else {
                index = i;
                status = THE_UNWORTHY_RECT_STATUS;
                state = new State(index, status);

                change_List = new ArrayList<>();
                change_List.add(state);
                q.add(change_List);
            }
        }

    }

    public my_Queue Binary_Search(double search_value, List<Double> a, int l, int r) {


        while (l <= r) {
            int mid = l + (r - l) / 2; // same as (l+r)/2 but avoid overflow;
            //??!?

            //Hightlight the interacting part of histogram
            status = SOMEWHAT_SPECIAL_RECT_STATUS;
            change_List = new ArrayList<>();
            for (int i = l; i < r + 1; i++) {
                state = new State(i, status);
                change_List.add(state);
            }
            q.add(change_List);


            //Highlight middle index
            status = THE_CHOSEN_RECT_STATUS;
            index = mid;
            change_List = new ArrayList<>();
            change_List.add(new State(index, status));

            q.add(change_List);

            if (search_value == a.get(mid)) {
                //When Found the value, highlight it, stop
                status = THE_TRUMP_RECT_STATUS;
                index = mid;
                change_List = new ArrayList<>();
                change_List.add(new State(index, status));

                q.add(change_List);
                return q;
            } else {
                status = THE_UNWORTHY_RECT_STATUS;
                index = mid;
                change_List = new ArrayList<>();
                change_List.add(new State(index, status));
                q.add(change_List);

                status = NORMAL_RECT_STATUS;
                change_List = new ArrayList<>();
                for (int i = l; i < r + 1; i++) {
                    state = new State(i, status);
                    change_List.add(state);
                }

                q.add(change_List);

                if (search_value < a.get(mid)) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
        }
        return q;
    }
}