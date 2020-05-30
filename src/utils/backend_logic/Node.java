package utils.backend_logic;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

public class Node {
    private int x, y;
    private double f, g, h;
    private Node Parent;


    public Node() {

    }

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Node(Node node) {
        this.x = node.x;
        this.y = node.y;
        this.f = node.f;
        this.g = node.y;
        this.h = node.h;
        this.Parent = node.get_Parent();
    }

    public boolean check_Overlap(Node e) {
        if (this.get_X() == e.get_X() && this.get_Y() == e.get_Y()) {
            return true;
        }
        return false;
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

    public Node get_Parent() {
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

    public void set_Parent(Node parent) {
        Parent = parent;
    }


    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                ", f=" + f
                + " }";

    }
}