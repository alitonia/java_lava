package utils.backend_logic;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

public class State_Blob {

    private int index;
    private int status;
    private double height;

//    public State_Blob(int index, int status) {
//        this.status = status;
//        this.index = index;
//    }

    public State_Blob(int index, int status, double height) {
        this.status = status;
        this.index = index;
        this.height = height;
    }
//
//    public State_Blob(State_Blob s) {
//        this.index = s.getIndex();
//        this.status = s.getStatus();
//    }

    public State_Blob(State_Blob s, double height) {
        this.index = s.getIndex();
        this.status = s.getStatus();
        this.height = height;
    }

//    public State_Blob(int x_Index, int y_Index, int status) {
//        this.index = Translator.flatten(x_Index, y_Index);
//        this.status = status;
//    }

//    public State_Blob(int x_Index, int y_Index, int status, double height) {
//        this.index = Translator.flatten(x_Index, y_Index);
//        this.status = status;
//        this.height = height;
//    }

    @Getter
    public int getStatus() {
        return status;
    }

    public int getIndex() {
        return index;
    }

    public double getHeight() {
        return height;
    }

    @Setter
    public void setStatus(int status) {
        this.status = status;
    }

    public void setIndex(int index) {
        this.index = index;
    }

//    public void setIndex(int x_Index, int y_Index) {
//        this.index = Translator.flatten(x_Index, y_Index);
//    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "State_Blob{" +
                "index=" + index +
                ", status=" + status +
                ", height=" + height +
                '}';
    }
}
