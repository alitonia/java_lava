package utils.front_end_logic;

import javafx.scene.shape.Rectangle;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import utils.Painter;

import static utils.constants.THE_DEFAULT_RECT_STATUS;


public class Colorful_Rectangle extends Rectangle {

    private int status;
    private Painter painter = new Painter();

    public Colorful_Rectangle() {
        super();
        //DEFAULT
        this.status = THE_DEFAULT_RECT_STATUS;
    }

    public Colorful_Rectangle(Colorful_Rectangle r) {
        super();
        this.setHeight(r.getHeight());
        this.setWidth(r.getWidth());
        this.setX(r.getX());
        this.setY(r.getY());
        this.setStatus(r.getStatus());
    }

    public Colorful_Rectangle(double width, double height) {
        super(width, height);
        this.setStatus(THE_DEFAULT_RECT_STATUS);
    }

    public Colorful_Rectangle(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.status = THE_DEFAULT_RECT_STATUS;
    }

    @Getter
    public int getStatus() {
        return status;
    }


    @Setter
    public void setStatus(int status) {
        this.status = status;
        painter.paint_by_Status(this);
    }

    @Override
    public String toString() {
        return "Colorful_Rectangle{ " +
                "X=" + getX() + ", " +
                "Y=" + getY() + ", " +
                "status=" + status + ", " +
                "height=" + getHeight() +
                " }";
    }
}
