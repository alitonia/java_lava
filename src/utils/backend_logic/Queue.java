package utils.backend_logic;

import java.util.ArrayList;

public class Queue {
    private ArrayList<State> list = new ArrayList<State>();

    public void add(State e) {
        list.add(e);
    }

    /**
     * @return the list
     */
    public ArrayList<State> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(ArrayList<State> list) {
        this.list = list;
    }
}
