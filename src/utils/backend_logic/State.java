package utils.backend_logic;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import utils.Translator;

public class State {

    private int index;
    private int status;


    public State(int index, int status) {
        this.status = status;
        this.index = index;
    }

    public State(State s) {
        this.index = s.getIndex();
        this.status = s.getStatus();
    }

    public State(int x_Index, int y_Index, int status) {
        this.index = Translator.flatten(x_Index, y_Index);
        this.status = status;
    }


    @Getter
    public int getStatus() {
        return status;
    }

    public int getIndex() {
        return index;
    }


    @Setter
    public void setStatus(int status) {
        this.status = status;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setIndex(int x_Index, int y_Index) {
        this.index = Translator.flatten(x_Index, y_Index);
    }


    @Override
    public String toString() {
        return "State{" +
                "index=" + index +
                ", status=" + status +
                '}';
    }
}
