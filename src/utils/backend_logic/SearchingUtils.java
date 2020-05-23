package utils.backend_logic;

import utils.consts;

import java.util.ArrayList;
import java.util.List;

import static utils.consts.*;

public class SearchingUtils {

    private my_Queue q = new my_Queue();
    private List<Double> internal_List;

    public void setInternal_List(List<Double> internal_List) {
        this.internal_List = internal_List;
    }

    public void Sequential_Search(double search_value, List<Double> a) {
        //?
//        my_Queue q = new my_Queue();
        int status;
        int index;
        State state;
        List<State> change_List;

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
//
//    public static my_Queue Binary_Search(int search_value, int[] a, int l, int r) {
//        my_Queue q = new my_Queue();
//        int status;
//        ArrayList<Integer> index;
//        State s;
//
//        while (l <= r) {
//            int mid = l + (r - l) / 2; // same as (l+r)/2 but avoid overflow;
//
//
//            //Save the range of array after each loop(left index,right index)
//            status = Constants.Binary_Search_Status.RANGE_STATUS;
//            index = new ArrayList<Integer>();
//            index.add(l);
//            index.add(r);
//            s = new State(status, index);
//            q.getList().add(s);
//
//
//            //Store the value of "mid" index
//            status = Constants.Binary_Search_Status.MID_STATUS;
//            index = new ArrayList<Integer>();
//            index.add(mid);
//            s = new State(status, index);
//            q.getList().add(s);
//
//            if (search_value == a[mid]) {
//                //When Found the value,store the index!!!
//                index = new ArrayList<Integer>();
//                status = Constants.Binary_Search_Status.FOUND_VALUE_STATUS;
//                index.add(mid);
//                s = new State(status, index);
//                q.getList().add(s);
//                return q;
//            } else {
//                status = Constants.Binary_Search_Status.NOT_MID_STATUS;
//                index = new ArrayList<Integer>();
//                index.add(mid);
//                s = new State(status, index);
//                q.getList().add(s);
//
//                status = Constants.Binary_Search_Status.DELETE_STATUS;
//                index = new ArrayList<Integer>();
//                index.add(l);
//                index.add(r);
//                s = new State(status, index);
//                q.getList().add(s);
//
//                if (search_value < a[mid]) {
//                    r = mid - 1;
//                } else {
//                    l = mid + 1;
//                }
//            }
//        }
//        return q;
//    }
}