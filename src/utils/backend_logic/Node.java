package utils.backend_logic;

import jdk.nashorn.internal.objects.annotations.Constructor;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import java.beans.ConstructorProperties;

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

    public boolean check_overlap(Node e) {
        if (this.getX() == e.getX() && this.getY() == e.getY()) {
            return true;
        }
        return false;
    }


    @Getter
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getF() {
        return f;
    }

    public double getG() {
        return g;
    }

    public double getH() {
        return h;
    }

    public Node getParent() {
        return Parent;
    }


    @Setter
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setF(double f) {
        this.f = f;
    }

    public void setG(double g) {
        this.g = g;
    }

    public void setH(double h) {
        this.h = h;
    }

    public void setParent(Node parent) {
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