package utils.backend_logic;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import utils.Log;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class history_Manager {

    private final Log my_Log;
    private final LinkedList<Map<String, List<State>>> history_Queue;
    private List<State> internal_List;
    private List<State> origin_List;

    private int current_step;
    private int queue_Length;


    public history_Manager() {
        history_Queue = new LinkedList<>();
        current_step = 0;
        queue_Length = 0;
        my_Log = new Log();
    }

    public void add(List<State> new_Changes) {
        if (internal_List != null && origin_List != null) {
            Map<String, List<State>> my_Map = new HashMap();

            //Make old_List
            List<State> previous_State = new ArrayList<>();
            for (State s : new_Changes) {
                previous_State.add(
                        new State(s.getIndex(),
                                internal_List.get(s.getIndex()).getStatus(),
                                internal_List.get(s.getIndex()).getHeight()));
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
            my_Log.print("Step added");

        } else {
            my_Log.print("Internal state/original state not specified!");
        }
    }

    public void clear() {
        history_Queue.clear();
        internal_List = null;
        origin_List = null;
        current_step = 0;
        queue_Length = 0;
        my_Log.print("Queue cleared!");
    }

    public List<State> get_Next() {
        List<State> return_List = null;

        if (is_Tail()) {
            my_Log.print("End of Queue");
        } else {
            return_List = history_Queue.get(current_step).get("New");
            current_step += 1;
            my_Log.print("Get next step successfully");
        }
        return return_List;
    }

    public List<State> get_Previous() {
        List<State> return_List = null;

        if (is_Top()) {
            my_Log.print("Start of Queue");
        } else {
            current_step -= 1;
            return_List = history_Queue.get(current_step).get("Old");
            my_Log.print("Get previous step successfully");
        }
        return return_List;
    }

    public void reset() {
        current_step = 0;
        my_Log.print("Queue set to 0");
    }

    public boolean is_Tail() {
        return current_step == queue_Length;
    }

    public boolean is_Top() {
        return current_step == 0;
    }

    public boolean is_Empty() {
        return queue_Length == 0;
    }


    @Getter
    public LinkedList<Map<String, List<State>>> get_History_Queue() {
        return history_Queue;
    }

    public int get_Queue_Length() {
        return queue_Length;
    }

    public List<State> get_Origin_List() {
        return origin_List;
    }


    @Setter
    //Might be used for debugging
    public void set_Internal_List(List<State> internal_List) {
        this.internal_List = internal_List;
    }


    public void set_Origin_List(List<State> origin_List) {
        this.origin_List = origin_List;

        //Copy to internal_List
        internal_List = new ArrayList<>();
        for (State s : origin_List) {
            internal_List.add(new State(s, s.getHeight()));
        }
    }


    public void print() {
        System.out.println("Queue elements:");
        AtomicInteger depth = new AtomicInteger();

        history_Queue.forEach(map -> {
            my_Log.print("Depth: " + depth);
            map.forEach((name, state_List) ->
                    my_Log.print(new StringBuilder()
                            .append(name).append("\n")
                            .append(state_List.toString()).toString())

            );
            my_Log.print("\n");
            depth.addAndGet(1);
        });
    }
}
