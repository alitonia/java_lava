package utils.backend_logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static utils.constants.*;

public class Sorting_Generator {

    private int status;
    private int index;
    private List<State_Blob> change_List;
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
            System.out.println("Current limiter: " + i);

            status = THE_OBSTACLE_RECT_STATUS;
            index = i;
            state_Blob = new State_Blob(index, status, height_List.get(index));

            change_List = new ArrayList<>();
            change_List.add(state_Blob);
            q.add(change_List);

            for (int j = height_List.size() - 1; j > i; j--) {

                status = THE_SPOTLIGHT_RECT_STATUS;
                index = j - 1;
                state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                change_List = new ArrayList<>();
                change_List.add(state_Blob);

                index = j;
                state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                change_List.add(state_Blob);

                q.add(change_List);

                System.out.println("Choosing "
                        + (j - 1) + ": " + height_List.get(j - 1) + ", "
                        + j + ": " + height_List.get(j));

                if (height_List.get(j) < height_List.get(j - 1)) {
                    System.out.println("Less than --> swap " + j + " " + (j - 1));

                    //Signal change
                    status = THE_SWAP_ABLE_RECT_STATUS;
                    index = j - 1;
                    state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                    change_List = new ArrayList<>();
                    change_List.add(state_Blob);

                    index = j;
                    state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                    change_List.add(state_Blob);

                    q.add(change_List);

                    //Now change
                    status = THE_SWAP_ABLE_RECT_STATUS;
                    index = j - 1;
                    state_Blob = new State_Blob(index, status, height_List.get(j));
                    change_List = new ArrayList<>();
                    change_List.add(state_Blob);

                    index = j;
                    state_Blob = new State_Blob(index, status, height_List.get(j - 1));
                    change_List.add(state_Blob);

                    q.add(change_List);
                    Collections.swap(height_List, j, j - 1);

                } else {
                    System.out.println("More than --> no swap");

                    //Signal can't change
                    status = THE_NOT_SWAP_ABLE_RECT_STATUS;
                    index = j - 1;
                    state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                    change_List = new ArrayList<>();
                    change_List.add(state_Blob);

                    index = j;
                    state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                    change_List.add(state_Blob);

                    q.add(change_List);
                }

                // back to normal
                status = THE_NORMAL_RECT_STATUS;
                index = j - 1;
                state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                change_List = new ArrayList<>();
                change_List.add(state_Blob);

                index = j;
                state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                change_List.add(state_Blob);

                q.add(change_List);
            }
            // First element is sorted --> paint it
            System.out.println("Sorted: " + i);

            status = THE_SUCCESSFUL_RECT_STATUS;
            index = i;
            state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);

            change_List = new ArrayList<>();
            change_List.add(state_Blob);
            q.add(change_List);

        }
        // Last element is not painted ---> paint it
        System.out.println("Sorted: " + (height_List.size() - 1));

        status = THE_SUCCESSFUL_RECT_STATUS;
        index = height_List.size() - 1;
        state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);

        change_List = new ArrayList<>();
        change_List.add(state_Blob);
        q.add(change_List);

    }


    public void Selection_Sort(List<Double> height_List, History_Manager q) {
        int min_Index;

        for (int i = 0; i < height_List.size() - 1; i++) {
            min_Index = i;
            for (int j = i + 1; j < height_List.size(); j++) {

                System.out.println("Selecting " + i + ": " + height_List.get(i));
                // paint current min and selecting rect
                change_List = new ArrayList<>();
                status = THE_OBSTACLE_RECT_STATUS;
                index = min_Index;
                state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                change_List.add(state_Blob);

                status = THE_SPOTLIGHT_RECT_STATUS;
                index = j;
                state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                change_List.add(state_Blob);

                q.add(change_List);

                if (height_List.get(j) < height_List.get(min_Index)) {
                    System.out.println("Rect " + j + ": " + height_List.get(j)
                            + " less than current min ---> swap");

                    // Signal if rect is chosen
                    change_List = new ArrayList<>();

                    status = THE_SWAP_ABLE_RECT_STATUS;
                    index = j;
                    state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                    change_List.add(state_Blob);

                    q.add(change_List);


                    // new min
                    change_List = new ArrayList<>();
                    status = THE_OBSTACLE_RECT_STATUS;
                    index = j;
                    state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                    change_List.add(state_Blob);

                    status = THE_SWAP_ABLE_RECT_STATUS;
                    index = min_Index;
                    state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                    change_List.add(state_Blob);

                    q.add(change_List);


                    // not min rect to normal color
                    change_List = new ArrayList<>();
                    status = THE_NORMAL_RECT_STATUS;
                    index = min_Index;
                    state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                    change_List.add(state_Blob);
                    q.add(change_List);

                    min_Index = j;
                    System.out.println("New min: " + min_Index + ": " + height_List.get(min_Index));

                } else {
                    // signal not chosen
                    System.out.println("Rect " + j + ": " + height_List.get(j)
                            + " greater than current min ---> no swap");
                    change_List = new ArrayList<>();
                    status = THE_UNWORTHY_RECT_STATUS;
                    index = j;
                    state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                    change_List.add(state_Blob);

                    q.add(change_List);

                    //back to normal

                    change_List = new ArrayList<>();
                    status = THE_NORMAL_RECT_STATUS;
                    index = j;
                    state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
                    change_List.add(state_Blob);

                    q.add(change_List);
                }

            }
            //swap
            change_List = new ArrayList<>();
            status = THE_SWAP_ABLE_RECT_STATUS;
            index = i;
            state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
            change_List.add(state_Blob);

            q.add(change_List);

            // i rect is sorted
            // change not sorted rect to normal,
            // except if i and min are the same rect

            if (i != min_Index) {
                change_List = new ArrayList<>();
                status = THE_SUCCESSFUL_RECT_STATUS;
                index = i;
                state_Blob = new State_Blob(index, status, height_List.get(min_Index));
                change_List.add(state_Blob);

                status = THE_NORMAL_RECT_STATUS;
                index = min_Index;
                state_Blob = new State_Blob(index, status, height_List.get(i));
                change_List.add(state_Blob);

                q.add(change_List);

            } else {
                change_List = new ArrayList<>();
                status = THE_SUCCESSFUL_RECT_STATUS;
                index = i;
                state_Blob = new State_Blob(index, status, height_List.get(min_Index));
                change_List.add(state_Blob);

                q.add(change_List);
            }
            System.out.println("Swap rect " + i + ", " + min_Index);
            System.out.println("Sorted: " + i);

            Collections.swap(height_List, i, min_Index);
        }

        // last element is sorted ---> color it
        System.out.println("Sorted: " + (height_List.size() - 1));

        change_List = new ArrayList<>();
        status = THE_SUCCESSFUL_RECT_STATUS;
        index = height_List.size() - 1;
        state_Blob = new State_Blob(index, status, NO_REPAINT_HEIGHT_SIGNAL);
        change_List.add(state_Blob);

        q.add(change_List);
    }


    public void merge_Sort(List<Double> height_List, int left, int right, History_Manager q) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            merge_Sort(height_List, left, mid, q);

            merge_Sort(height_List, mid + 1, right, q);

            merge(height_List, left, mid, right, q);
        }
    }

    public void merge(List<Double> height_List, int left, int mid, int right, History_Manager q) {
        System.out.println("Range: " + left + " - " + mid + " - " + right);

        int i, j, k;
        int n1 = mid - left + 1;
        int n2 = right - mid;

        List<Double> L = new ArrayList<>();
        List<Double> R = new ArrayList<>();

        //focusing
        change_List = new ArrayList<>();
        status = THE_FOCUSED_RECT_STATUS;
        for (int l = left; l < mid + 1; l++) {
            change_List.add(new State_Blob(l, status, NO_REPAINT_HEIGHT_SIGNAL));
        }

        status = THE_SECOND_SPOTLIGHT_RECT_STATUS;
        for (int l = mid + 1; l < right + 1; l++) {
            change_List.add(new State_Blob(l, status, NO_REPAINT_HEIGHT_SIGNAL));
        }
        q.add(change_List);

        for (i = 0; i < n1; i++) {
            L.add(height_List.get(left + i));
        }
        for (j = 0; j < n2; j++) {
            R.add(height_List.get(mid + j + 1));
        }

        i = 0;
        j = 0;
        k = left;
        System.out.println("Sort order: ");

        while (i < n1 && j < n2) {
            if (L.get(i) <= R.get(j)) {
                height_List.set(k, L.get(i));
                System.out.print((left + i) + " -> ");

                change_List = new ArrayList<>();
                change_List.add(
                        new State_Blob(left + i,
                                THE_GATE_RECT_STATUS,
                                NO_REPAINT_HEIGHT_SIGNAL)
                );
                q.add(change_List);

                i += 1;
            } else {
                height_List.set(k, R.get(j));
                System.out.print((mid + j + 1) + " -> ");

                change_List = new ArrayList<>();
                change_List.add(
                        new State_Blob(mid + j + 1,
                                THE_GATE_RECT_STATUS,
                                NO_REPAINT_HEIGHT_SIGNAL)
                );
                q.add(change_List);
                j += 1;
            }
            k += 1;
        }
        while (i < n1) {
            height_List.set(k, L.get(i));
            System.out.print((left + i) + " -> ");

            change_List = new ArrayList<>();
            change_List.add(
                    new State_Blob(left + i,
                            THE_GATE_RECT_STATUS,
                            NO_REPAINT_HEIGHT_SIGNAL)
            );
            q.add(change_List);

            i += 1;
            k += 1;
        }
        while (j < n2) {
            height_List.set(k, R.get(j));
            System.out.print((mid + j + 1) + " -> ");

            change_List = new ArrayList<>();
            change_List.add(
                    new State_Blob(mid + j + 1,
                            THE_GATE_RECT_STATUS,
                            NO_REPAINT_HEIGHT_SIGNAL)
            );
            q.add(change_List);

            j += 1;
            k += 1;
        }
        System.out.println();

        System.out.println("Sorted: " + left + " - " + right);

        change_List = new ArrayList<>();
        status = THE_SUCCESSFUL_RECT_STATUS;
        for (int l = left; l < right + 1; l++) {
            change_List.add(new State_Blob(l, status, height_List.get(l)));
        }
        q.add(change_List);
    }


    public void quick_Sort(List<Double> height_List, int start, int end, History_Manager q) {
        // has equal so it iterate through all rect --> color all
        if (start <= end) {
            int pivot = partition(height_List, start, end, q);

            // Recursively quick_Sort on 2 sides of pivot
            quick_Sort(height_List, start, pivot - 1, q);
            quick_Sort(height_List, pivot + 1, end, q);
            System.out.println(height_List.toString() + " ... " + start + " ... " + end);
        }
    }

    private int partition(List<Double> height_List, int start, int end, History_Manager q) {

        double pivot = height_List.get(end);
        System.out.println("Pivot " + end + ": " + pivot);

        //coloring pivot
        change_List = new ArrayList<>();
        change_List.add(new State_Blob(end, THE_OBSTACLE_RECT_STATUS, NO_REPAINT_HEIGHT_SIGNAL));
        q.add(change_List);

        int i = (start - 1); // index of smaller element

        for (int j = start; j < end; j++) {
            // selecting another
            System.out.println("Selecting " + j + ": " + height_List.get(j));

            change_List = new ArrayList<>();
            change_List.add(new State_Blob(j, THE_FOCUSED_RECT_STATUS, NO_REPAINT_HEIGHT_SIGNAL));
            q.add(change_List);

            // If current element is smaller than the pivot
            if (height_List.get(j) < pivot) {
                System.out.println(
                        "Current value: " + height_List.get(j) + " < " + pivot
                                + " -----> swap");
                // signal can swap
                change_List = new ArrayList<>();
                change_List.add(new State_Blob(j, THE_SPOTLIGHT_RECT_STATUS, NO_REPAINT_HEIGHT_SIGNAL));
                q.add(change_List);

                i += 1;
                Collections.swap(height_List, i, j);

                //now swap
                change_List = new ArrayList<>();
                change_List.add(new State_Blob(i, THE_SPOTLIGHT_RECT_STATUS, height_List.get(i)));
                change_List.add(new State_Blob(j, THE_SPOTLIGHT_RECT_STATUS, height_List.get(j)));
                q.add(change_List);

                //back to normal
                change_List = new ArrayList<>();
                change_List.add(new State_Blob(i, THE_NORMAL_RECT_STATUS, NO_REPAINT_HEIGHT_SIGNAL));
                change_List.add(new State_Blob(j, THE_NORMAL_RECT_STATUS, NO_REPAINT_HEIGHT_SIGNAL));

                q.add(change_List);


            } else {
                System.out.println(
                        "Current value: " + height_List.get(j) + " >= " + pivot
                                + " -----> not swap");
                //signal can't swap
                change_List = new ArrayList<>();
                change_List.add(new State_Blob(j, THE_UNWORTHY_RECT_STATUS, NO_REPAINT_HEIGHT_SIGNAL));
                q.add(change_List);

                //unselect rect
                change_List = new ArrayList<>();
                change_List.add(new State_Blob(j, THE_NORMAL_RECT_STATUS, NO_REPAINT_HEIGHT_SIGNAL));
                q.add(change_List);
            }
        }
        // swap pivot and last index
        if (i + 1 != end) {
            // selecting
            change_List = new ArrayList<>();
            change_List.add(new State_Blob(i + 1, THE_SPOTLIGHT_RECT_STATUS, NO_REPAINT_HEIGHT_SIGNAL));
            q.add(change_List);

            //now swap
            change_List = new ArrayList<>();
            change_List.add(new State_Blob(i + 1, THE_OBSTACLE_RECT_STATUS, height_List.get(end)));
            change_List.add(new State_Blob(end, THE_SPOTLIGHT_RECT_STATUS, height_List.get(i + 1)));
            q.add(change_List);

            //back to normal
            change_List = new ArrayList<>();
            change_List.add(new State_Blob(end, THE_NORMAL_RECT_STATUS, NO_REPAINT_HEIGHT_SIGNAL));

            //show sorted rect
            change_List.add(new State_Blob(i + 1, THE_SUCCESSFUL_RECT_STATUS, NO_REPAINT_HEIGHT_SIGNAL));
            q.add(change_List);

        } else {
            // selecting
            change_List = new ArrayList<>();
            change_List.add(new State_Blob(i + 1, THE_SPOTLIGHT_RECT_STATUS, NO_REPAINT_HEIGHT_SIGNAL));
            q.add(change_List);

            //show sorted
            change_List = new ArrayList<>();
            change_List.add(new State_Blob(i + 1, THE_SUCCESSFUL_RECT_STATUS, NO_REPAINT_HEIGHT_SIGNAL));
            q.add(change_List);
        }
        System.out.println("Swap " + (i + 1) + ", " + end);
        Collections.swap(height_List, i + 1, end);

        System.out.println("Sorted: " + (i + 1));
        return i + 1;
    }


    public void insertion_Sort(List<Double> height_List, History_Manager q) {
        int size = height_List.size();

        for (int i = 1; i < size; i++) {
            Double key = height_List.get(i);
            System.out.println("Key: " + key);

            // mark "pivot"
            change_List = new ArrayList<>();
            change_List.add(new State_Blob(i, THE_OBSTACLE_RECT_STATUS, NO_REPAINT_HEIGHT_SIGNAL));
            q.add(change_List);

            int j = i - 1;

            while (j >= 0 && height_List.get(j) > key) {
                // selecting
                change_List = new ArrayList<>();
                change_List.add(new State_Blob(j, THE_SPOTLIGHT_RECT_STATUS, NO_REPAINT_HEIGHT_SIGNAL));
                change_List.add(new State_Blob(j + 1, THE_SPOTLIGHT_RECT_STATUS, NO_REPAINT_HEIGHT_SIGNAL));
                q.add(change_List);

                //swap
                System.out.println("Rect " + (j + 1) + " to value " + height_List.get(j));

                height_List.set(j + 1, height_List.get(j));

                change_List = new ArrayList<>();
                change_List.add(new State_Blob(j, THE_SPOTLIGHT_RECT_STATUS, height_List.get(j)));
                change_List.add(new State_Blob(j + 1, THE_SPOTLIGHT_RECT_STATUS, height_List.get(j + 1)));
                q.add(change_List);

                change_List = new ArrayList<>();
                change_List.add(new State_Blob(j, THE_SUCCESSFUL_RECT_STATUS, NO_REPAINT_HEIGHT_SIGNAL));
                change_List.add(new State_Blob(j + 1, THE_SUCCESSFUL_RECT_STATUS, NO_REPAINT_HEIGHT_SIGNAL));
                q.add(change_List);

                j -= 1;
            }

            change_List = new ArrayList<>();
            change_List.add(new State_Blob(j + 1, THE_SPOTLIGHT_RECT_STATUS, NO_REPAINT_HEIGHT_SIGNAL));
            q.add(change_List);

            System.out.println("Rect " + (j + 1) + "to value " + key);

            height_List.set(j + 1, key);

            change_List = new ArrayList<>();
            change_List.add(new State_Blob(j + 1, THE_OBSTACLE_RECT_STATUS, height_List.get(j + 1)));
            q.add(change_List);


            System.out.println("Sorted: " + 0 + " - " + (i + 1));

            change_List = new ArrayList<>();
            for (int k = 0; k < i + 1; k++) {
                change_List.add(new State_Blob(k, THE_SUCCESSFUL_RECT_STATUS, NO_REPAINT_HEIGHT_SIGNAL));
            }
            q.add(change_List);

        }
    }
}

