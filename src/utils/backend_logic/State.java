package utils.backend_logic;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import java.util.ArrayList;

public class State {

    private ArrayList<Integer> my_Index = new ArrayList<>();
    private int index;
    private int status;

    public State() {

    }

//    //old:
//    public State(int status, ArrayList<Integer> my_Index) {
//        this.status = status;
//        this.my_Index = my_Index;
//    }

    //new:
    public State(int index, int status) {
        this.status = status;
        this.index = index;
    }

    public State(State s) {
        this.index = s.getIndex();
        this.status = s.getStatus();
    }


    @Getter
    public int getStatus() {
        return status;
    }

    public ArrayList<Integer> getMy_Index() {
        return my_Index;
    }

    public int getIndex() {
        return index;
    }


    @Setter
    public void setStatus(int status) {
        this.status = status;
    }

    public void setMy_Index(ArrayList<Integer> my_Index) {
        this.my_Index = my_Index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "State{" +
                "index=" + index +
                ", status=" + status +
                '}';
    }
}
