package utils.backend_logic;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

public class Graph_Node {
    private int x, y;
    private double f, g, h;
    private Graph_Node Parent;


    public Graph_Node() {

    }

    public Graph_Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Graph_Node(Graph_Node graph_Node) {
        this.x = graph_Node.x;
        this.y = graph_Node.y;
        this.f = graph_Node.f;
        this.g = graph_Node.y;
        this.h = graph_Node.h;
        this.Parent = graph_Node.get_Parent();
    }

    public boolean check_Overlap(Graph_Node e) {
        return this.get_X() == e.get_X() && this.get_Y() == e.get_Y();
    }


    @Getter
    public int get_X() {
        return x;
    }

    public int get_Y() {
        return y;
    }

    public double get_F() {
        return f;
    }

    public double get_G() {
        return g;
    }

    public double getH() {
        return h;
    }

    public Graph_Node get_Parent() {
        return Parent;
    }


    @Setter
    public void set_X(int x) {
        this.x = x;
    }

    public void set_Y(int y) {
        this.y = y;
    }

    public void set_F(double f) {
        this.f = f;
    }

    public void set_G(double g) {
        this.g = g;
    }

    public void set_H(double h) {
        this.h = h;
    }

    public void set_Parent(Graph_Node parent) {
        Parent = parent;
    }


    @Override
    public String toString() {
        return "Graph_Node{" +
                "x=" + x +
                ", y=" + y +
                ", f=" + f
                + " }";

    }
}