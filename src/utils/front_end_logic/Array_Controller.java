package utils.front_end_logic;


import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import sample.Controller;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static utils.consts.*;


public class Array_Controller {

    private Controller c;
    private static ObservableList<Colorful_Rectangle> colorful_rectangles;
    private Log my_Log = new Log();
    private Painter painter = new Painter();

    private static int length;

    private static boolean isPaintable = false;

    public Array_Controller(Controller c) {
        this.c = c;

    }

    public void make(int length) {
        //Make a array of float between min and max value
        //Status = Normal
        Array_Controller.length = length;

        colorful_rectangles = FXCollections.observableArrayList();


        colorful_rectangles.addListener(new ListChangeListener<Colorful_Rectangle>() {
            @Override
            public void onChanged(Change<? extends Colorful_Rectangle> ch) {
                c.paint_Board();
                my_Log.print("Array updated!");
                my_Log.print(ch.toString());
            }
        });


        Pane visual_Board = c.getVisual_board();
        float width_per_rect = (float) visual_Board.getWidth() / NUMBER_OF_RECTANGLE;
        for (int i = 0; i < length; i++) {
            Colorful_Rectangle rectangle = new Colorful_Rectangle();
            rectangle.setX(visual_Board.getLayoutX() + i * width_per_rect);
            rectangle.setY(visual_Board.getLayoutY());

            rectangle.setHeight((float) (
                    ThreadLocalRandom.current().nextDouble(
                            MINIMUM_RECT_HEIGHT,
                            MAXIMUM_RECT_HEIGHT
                    )) * visual_Board.getHeight());
            rectangle.setWidth(width_per_rect);

            rectangle.setStatus(NORMAL_RECT);
            painter.paint_by_Status(rectangle);

            colorful_rectangles.add(rectangle);
        }
        setPaintable(true);
    }


    public void swap(int index_1, int index_2) {
        Colorful_Rectangle first_rectangle = colorful_rectangles.get(index_1);
        Colorful_Rectangle second_rectangle = colorful_rectangles.get(index_2);

        Colorful_Rectangle new_first_rectangle = new Colorful_Rectangle(second_rectangle);
        new_first_rectangle.setX(first_rectangle.getX());
        new_first_rectangle.setY(first_rectangle.getY());
        painter.paint_by_Status(new_first_rectangle);

        Colorful_Rectangle new_second_rectangle = new Colorful_Rectangle(first_rectangle);
        new_second_rectangle.setX(second_rectangle.getX());
        new_second_rectangle.setY(second_rectangle.getY());
        painter.paint_by_Status(new_second_rectangle);

        colorful_rectangles.set(index_1, new_first_rectangle);
        colorful_rectangles.set(index_2, new_second_rectangle);
    }


    public void setStatus(int index, int status) {
        Colorful_Rectangle rectangle = new Colorful_Rectangle(colorful_rectangles.get(index));

        rectangle.setStatus(status);
        painter.paint_by_Status(rectangle);
        colorful_rectangles.set(index, rectangle);
    }


    @Getter
    public int getLength() {
        return length;
    }

    public static boolean isPaintable() {
        return isPaintable;
    }


    public List<Colorful_Rectangle> getColorful_rectangles() {
        return colorful_rectangles;
    }

    @Setter
    public static void setPaintable(boolean paintable) {
        isPaintable = paintable;
    }

    public void setColorful_rectangle(int index, int status) {
        colorful_rectangles.get(index).setStatus(status);
    }

}
