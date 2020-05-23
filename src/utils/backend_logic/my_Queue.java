package utils.backend_logic;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import utils.front_end_logic.Log;

import java.util.*;

public class my_Queue {
    //Old:
//    private ArrayList<State> list = new ArrayList<State>();

    //New:
    private static LinkedList<Map<String, List<State>>> history_Queue;
    private static List<State> internal_List;
    private static List<State> origin_List;

    private static int current_step;
    private static int queue_Length;

    Log my_Log = new Log();


    public my_Queue() {
        history_Queue = new LinkedList<>();
//        this.list = new ArrayList<>();
        internal_List = new ArrayList<>();
        current_step = 0;
        queue_Length = 0;
    }

    //old:
//    public void add(State e) {
//        list.add(e);
//    }

    //new:
    public void add(List<State> new_Changes) {
        Map<String, List<State>> my_Map = new HashMap();

        //Make old_List
        List<State> previous_State = new ArrayList<>();
        for (State s : new_Changes) {
            previous_State.add(new State(s.getIndex(), internal_List.get(s.getIndex()).getStatus()));
        }
        my_Map.put("Old", previous_State);


        //Make new_List
        my_Map.put("New", new_Changes);

//        Change internal state accordingly
        for (State s : new_Changes) {
            State destination = internal_List.get(s.getIndex());
            destination.setStatus(s.getStatus());
        }

        queue_Length += 1;
        history_Queue.offer(my_Map);
    }

    public void clear() {
        history_Queue = new LinkedList<>();
//        this.list = new ArrayList<>();
        internal_List = new ArrayList<>();
        current_step = 0;
        queue_Length = 0;
    }

    public List<State> get_Next_Step() {
        List<State> return_List = null;

        if (current_step == queue_Length) {
            my_Log.print("End of Queue");
        } else {
            return_List = new ArrayList<>();
            return_List = history_Queue.get(current_step).get("New");
            current_step += 1;
        }
        return return_List;
    }

    public List<State> get_Previous_Step() {
        List<State> return_List = null;

        if (current_step == 0) {
            my_Log.print("Start of Queue");
        } else {
            return_List = new ArrayList<>();
            current_step -= 1;
            return_List = history_Queue.get(current_step).get("Old");
        }
        return return_List;
    }

    public List<State> back_to_Start() {
        current_step = 0;
        return origin_List;
    }

    public boolean isEnding() {
        return current_step == queue_Length;
    }

    @Getter
//    public ArrayList<State> getList() {
//        return list;
//    }

    public static LinkedList<Map<String, List<State>>> getHistory_Queue() {
        return history_Queue;
    }

    public static List<State> getInternal_List() {
        return internal_List;
    }

    public int getCurrent_step() {
        return current_step;
    }

    public int getQueue_Length() {
        return queue_Length;
    }

    @Setter
//    public void setList(ArrayList<State> list) {
//        this.list = list;
//    }

    public static void setInternal_List(List<State> internal_List) {
        my_Queue.internal_List = internal_List;
//        //Copy of original version
//        for (State s : internal_List) {
//            my_Queue.origin_List.add(new State(s));
//        }
    }

    public static void setOrigin_List(List<State> origin_List) {
        my_Queue.origin_List = origin_List;
    }

    public static void print() {
        System.out.println("Hello");
        int i = 0;
        for (Map<String, List<State>> x : history_Queue) {

            System.out.println(i);
            x.values().forEach(System.out::println);
            i += 1;
        }
    }
}
