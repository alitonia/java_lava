package Incoming_features;

import utils.backend_logic.History_Manager;
import utils.backend_logic.State_Blob;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static utils.constants.*;

public class Sorting_Utils {

    private int status;
    private int index;
    private List<State_Blob> my_Change;
    private State_Blob state_Blob;


    public void Bubble_Sort(List<Double> height_List, History_Manager q) {

        // Steps:
        // All normal color
        // 1. Select FLAG
        // 2. Select CHOOSING
        // 3. Select CHANGING/ NOT_CHANGING
        // 4. Change ( if CHANGE)
        // 5. Normal color
        // 6. End 1 iteration, last -> SORTED


        for (int i = 0; i < height_List.size() - 1; i++) {
            status = THE_OBSTACLE_RECT_STATUS;
            index = i;
            state_Blob = new State_Blob(index, status, height_List.get(index));

            my_Change = new ArrayList<>();
            my_Change.add(state_Blob);
            q.add(my_Change);

            for (int j = height_List.size() - 1; j > i; j--) {
                status = THE_SPOTLIGHT_RECT_STATUS;
                index = j - 1;
                state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                my_Change = new ArrayList<>();
                my_Change.add(state_Blob);

                index = j;
                state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                my_Change.add(state_Blob);

                q.add(my_Change);

                if (height_List.get(j) < height_List.get(j - 1)) {
                    //Signal change
                    status = THE_SWAP_ABLE_RECT_STATUS;
                    index = j - 1;
                    state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                    my_Change = new ArrayList<>();
                    my_Change.add(state_Blob);

                    index = j;
                    state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                    my_Change.add(state_Blob);

                    q.add(my_Change);

                    //Now change
                    status = THE_SWAP_ABLE_RECT_STATUS;
                    index = j - 1;
                    state_Blob = new State_Blob(index, status, height_List.get(j));
                    my_Change = new ArrayList<>();
                    my_Change.add(state_Blob);

                    index = j;
                    state_Blob = new State_Blob(index, status, height_List.get(j - 1));
                    my_Change.add(state_Blob);

                    q.add(my_Change);
                    Collections.swap(height_List, j, j - 1);

                } else {
                    //Signal can't change
                    status = THE_NOT_SWAP_ABLE_RECT_STATUS;
                    index = j - 1;
                    state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                    my_Change = new ArrayList<>();
                    my_Change.add(state_Blob);

                    index = j;
                    state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                    my_Change.add(state_Blob);

                    q.add(my_Change);
                }

                // back to normal
                status = THE_NORMAL_RECT_STATUS;
                index = j - 1;
                state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                my_Change = new ArrayList<>();
                my_Change.add(state_Blob);

                index = j;
                state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                my_Change.add(state_Blob);

                q.add(my_Change);
            }
            // First element is sorted --> paint it
            status = THE_SUCCESSFUL_RECT_STATUS;
            index = i;
            state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);

            my_Change = new ArrayList<>();
            my_Change.add(state_Blob);
            q.add(my_Change);

        }
        // Last element is not painted ---> paint it
        status = THE_SUCCESSFUL_RECT_STATUS;
        index = height_List.size() - 1;
        state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);

        my_Change = new ArrayList<>();
        my_Change.add(state_Blob);
        q.add(my_Change);

    }

    public void Selection_Sort(List<Double> height_List, History_Manager q) {

        int min_Index = 0;


        for (int i = 0; i < height_List.size() - 1; i++) {
            min_Index = i;
            for (int j = i + 1; j < height_List.size(); j++) {

                // paint current min and selecting rect
                my_Change = new ArrayList<>();
                status = THE_OBSTACLE_RECT_STATUS;
                index = min_Index;
                state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                my_Change.add(state_Blob);

                status = THE_SPOTLIGHT_RECT_STATUS;
                index = j;
                state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                my_Change.add(state_Blob);

                q.add(my_Change);

                if (height_List.get(j) < height_List.get(min_Index)) {
                    // Signal if rect is chosen
                    my_Change = new ArrayList<>();

                    status = THE_SWAP_ABLE_RECT_STATUS;
                    index = j;
                    state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                    my_Change.add(state_Blob);

                    q.add(my_Change);


                    // new min
                    my_Change = new ArrayList<>();
                    status = THE_OBSTACLE_RECT_STATUS;
                    index = j;
                    state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                    my_Change.add(state_Blob);

                    status = THE_SWAP_ABLE_RECT_STATUS;
                    index = min_Index;
                    state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                    my_Change.add(state_Blob);

                    q.add(my_Change);


                    // not min rect to normal color
                    my_Change = new ArrayList<>();
                    status = THE_NORMAL_RECT_STATUS;
                    index = min_Index;
                    state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                    my_Change.add(state_Blob);
                    q.add(my_Change);

                    min_Index = j;

                } else {
                    // signal not chosen
                    my_Change = new ArrayList<>();
                    status = THE_UNWORTHY_RECT_STATUS;
                    index = j;
                    state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                    my_Change.add(state_Blob);

                    q.add(my_Change);

                    //back to normal

                    my_Change = new ArrayList<>();
                    status = THE_NORMAL_RECT_STATUS;
                    index = j;
                    state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                    my_Change.add(state_Blob);

                    q.add(my_Change);
                }

            }
            //swap
            my_Change = new ArrayList<>();
            status = THE_SWAP_ABLE_RECT_STATUS;
            index = i;
            state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
            my_Change.add(state_Blob);

            q.add(my_Change);

            // i rect is sorted
            // change not sorted rect to normal,
            // except if i and min are the same rect

            if (i != min_Index) {
                my_Change = new ArrayList<>();
                status = THE_SUCCESSFUL_RECT_STATUS;
                index = i;
                state_Blob = new State_Blob(index, status, height_List.get(min_Index));
                my_Change.add(state_Blob);

                status = THE_NORMAL_RECT_STATUS;
                index = min_Index;
                state_Blob = new State_Blob(index, status, height_List.get(i));
                my_Change.add(state_Blob);

                q.add(my_Change);

            } else {
                my_Change = new ArrayList<>();
                status = THE_SUCCESSFUL_RECT_STATUS;
                index = i;
                state_Blob = new State_Blob(index, status, height_List.get(min_Index));
                my_Change.add(state_Blob);

                q.add(my_Change);
            }
            Collections.swap(height_List, i, min_Index);
        }

        // last element is sorted ---> color it
        my_Change = new ArrayList<>();
        status = THE_SUCCESSFUL_RECT_STATUS;
        index = height_List.size() - 1;
        state_Blob = new State_Blob(index, status, height_List.get(min_Index));
        my_Change.add(state_Blob);

        q.add(my_Change);
    }

//    public static void Merge(int[] arr, int l, int m, int r) {
//        int i, j, k;
//        int n1 = m - l + 1;
//        int n2 = r - m;
//
//        int[] L = new int[n1];
//        int[] R = new int[n2];
//
//        for (i = 0; i < n1; i++) {
//            L[i] = arr[l + i];
//        }
//        for (j = 0; j < n2; j++) {
//            R[j] = arr[m + j + 1];
//        }
//
//        i = 0;
//        j = 0;
//        k = l;
//
//
//        while (i < n1 && j < n2) {
//            if (L[i] <= R[j]) {
//                arr[k] = L[i];
//                i++;
//            } else {
//                arr[k] = R[j];
//                j++;
//            }
//            k++;
//        }
//        while (i < n1) {
//            arr[k] = L[i];
//            i++;
//            k++;
//        }
//        while (j < n2) {
//            arr[k] = R[j];
//            j++;
//            k++;
//        }
//    }
//
//    public static void Merge_Sort(int[] arr, int left, int right) {
//        if (left < right) {
//            int mid = left + (right - left) / 2;
//
//            Merge_Sort(arr, left, mid);
//
//            Merge_Sort(arr, mid + 1, right);
//
//            Merge(arr, left, mid, right);
//        }
//    }
//
//    public static int Find_max_value(ArrayList<Integer> arr) {
//        int max_value = arr.get(0);
//        for (int i = 1; i < arr.size(); i++) {
//            if (max_value < arr.get(i)) {
//                max_value = arr.get(i);
//            }
//        }
//        return max_value;
//    }
//
//    public static void bucket_sort_for_each_part(ArrayList<Integer> arr) {
//        int max_value = Find_max_value(arr);
//
//        int start_pos = 0;
//
//        int[] bucket_array = new int[max_value + 1];
//
//        for (int i = 0; i < arr.size(); i++) {
//            bucket_array[arr.get(i)]++;
//        }
//
//        arr.clear();
//
//        for (int i = 0; i < bucket_array.length; i++) {
//            for (int j = 0; j < bucket_array[i]; j++) {
//                arr.add(i);
//            }
//        }
//    }
//
//    public static void Bucket_Sort(int[] arr) {
//        ArrayList<Integer> Pos = new ArrayList<Integer>();
//
//        ArrayList<Integer> Neg = new ArrayList<Integer>();
//
//
//        for (int i = 0; i < arr.length; i++) {
//            if (arr[i] < 0) {
//                Neg.add(-1 * arr[i]);
//            } else {
//                Pos.add(arr[i]);
//            }
//        }
//
//        bucket_sort_for_each_part(Neg);
//        bucket_sort_for_each_part(Pos);
//
//        for (int i = 0; i < Neg.size(); i++) {
//            arr[i] = -1 * Neg.get(Neg.size() - i - 1);
//        }
//
//        for (int i = Neg.size(); i < arr.length; i++) {
//            arr[i] = Pos.get(i - Neg.size());
//        }
//    }
}
