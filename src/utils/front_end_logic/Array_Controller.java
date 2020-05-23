package utils.front_end_logic;


import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import sample.Controller;

import java.awt.*;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static utils.consts.*;


public class Array_Controller {

    private Controller c;
    private static ObservableList<Colorful_Rectangle> colorful_rectangles;
    private Log my_Log = new Log();
    private Painter painter = new Painter();

    private int status;

    private static int length;

//    private static boolean isPaintable = false;

    public Array_Controller(Controller c) {
        this.c = c;
        my_Log.print("Array_controller created.");
    }


    private int partition(List<Colorful_Rectangle> arr, int low, int high) {
        double pivot = arr.get(high).getHeight();
        int i = (low - 1); // index of smaller element
        for (int j = low; j < high; j++) {
            // If current element is smaller than the pivot
            if (arr.get(j).getHeight() < pivot) {
                i++;

                // swap arr[i] and arr[j]
//                int temp = arr[i];
//                arr[i] = arr[j];
//                arr[j] = temp;
                swap(i, j);
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        swap(i + 1, high);

        return i + 1;
    }


    /* The main function that implements QuickSort()
      arr[] --> Array to be sorted,
      low  --> Starting index,
      high  --> Ending index */
    private void sort(List<Colorful_Rectangle> arr, int low, int high) {
        if (low < high) {
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partition(arr, low, high);

            // Recursively sort elements before
            // partition and after partition
            sort(arr, low, pi - 1);
            sort(arr, pi + 1, high);
        }
    }


    public void make(int number_of_Rectangles) {
        //Make a array of float between min and max value
        //Status = Normal
        c.clean_Board();
        Array_Controller.length = number_of_Rectangles;
        colorful_rectangles = FXCollections.observableArrayList();
        Pane visual_Board = c.getVisual_board();

        float width_per_rect = (float) visual_Board.getWidth() / NUMBER_OF_RECTANGLE;

        for (int i = 0; i < number_of_Rectangles; i++) {
            Colorful_Rectangle rectangle = new Colorful_Rectangle();
            rectangle.setHeight((float) (
                    ThreadLocalRandom.current().nextDouble(
                            MINIMUM_RECT_HEIGHT,
                            MAXIMUM_RECT_HEIGHT
                    )) * visual_Board.getHeight());
            rectangle.setWidth(width_per_rect);

            rectangle.setX(visual_Board.getLayoutX() + i * width_per_rect);
            rectangle.setY(visual_Board.getLayoutY() +
                    (visual_Board.getHeight() - rectangle.getHeight()));
            rectangle.setStatus(NORMAL_RECT_STATUS);

            colorful_rectangles.add(rectangle);
        }
//        setPaintable(true);

        c.paint_Board(colorful_rectangles);
    }


    //Make ordered histogram
    public void make_Ordered(int number_of_Rectangles) {
        make(number_of_Rectangles);
        sort(colorful_rectangles, 0, colorful_rectangles.size() - 1);
    }

    //Make 2D rectangles
    public void make_2D(int width_in_rectangle, int height_in_rectangle) {
        c.clean_Board();

        Pane visual_Board = c.getVisual_board();
        colorful_rectangles = FXCollections.observableArrayList();

        int total_rect = width_in_rectangle * height_in_rectangle;
        for (int i = 0; i < total_rect; i++) {
            Colorful_Rectangle rectangle = new Colorful_Rectangle();

            rectangle.setWidth(visual_Board.getWidth() / width_in_rectangle - 1);
            rectangle.setHeight(visual_Board.getHeight() / height_in_rectangle - 1);

            rectangle.setX(10 + visual_Board.getLayoutX() + (i % width_in_rectangle) * rectangle.getWidth());
//                    + (double) ((i % width_in_rectangle) / width_in_rectangle) * visual_Board.getLayoutX());
            rectangle.setY(10 + visual_Board.getLayoutY() + (i / height_in_rectangle) * rectangle.getHeight());
//                    + (double) (Math.floor(i / height_in_rectangle) / height_in_rectangle) * visual_Board.getLayoutY());

            if (i == 0 || i == total_rect - 1) {
                rectangle.setStatus(GATE_RECT_STATUS);
            } else {
                List<Integer> choice = Arrays.asList(
                        NORMAL_RECT_STATUS, OBSTACLE_RECT_STATUS,
                        NORMAL_RECT_STATUS
                );
                rectangle.setStatus(choice.get(ThreadLocalRandom.current().nextInt(choice.size())));

            }
            painter.paint_by_Status(rectangle);

            colorful_rectangles.add(rectangle);
        }
        c.paint_Board(colorful_rectangles);

    }


    //Change status of 1 colorful rectangle
    public void setStatus(int index, int status) {
        Colorful_Rectangle rectangle = colorful_rectangles.get(index);
        rectangle.setStatus(status);
        painter.paint_by_Status(rectangle);
    }


    //swap histogram elements
    public void swap(int index_1, int index_2) {
        Pane visual_Board = c.getVisual_board();
        //Swap coordinate of index_1 and index_2 elements
        Colorful_Rectangle first_rectangle = colorful_rectangles.get(index_1);
        Colorful_Rectangle second_rectangle = colorful_rectangles.get(index_2);

        double first_X = first_rectangle.getX();
//        double first_Y = first_rectangle.getY();

        first_rectangle.setX(second_rectangle.getX());
        first_rectangle.setY(visual_Board.getLayoutY() +
                (visual_Board.getHeight() - first_rectangle.getHeight()));

        second_rectangle.setX(first_X);
        second_rectangle.setY(visual_Board.getLayoutY() +
                (visual_Board.getHeight() - second_rectangle.getHeight()));

        //Do fictional swap in List
        Collections.swap(colorful_rectangles, index_1, index_2);
    }

    @Getter
    public int getLength() {
        return length;
    }

//    public static boolean isPaintable() {
//        return isPaintable;
//    }


    public List<Colorful_Rectangle> getColorful_rectangles() {
        return colorful_rectangles;
    }

    @Setter
//    public static void setPaintable(boolean paintable) {
//        isPaintable = paintable;
//    }

    public void setColorful_rectangle(int index, int status) {
        colorful_rectangles.get(index).setStatus(status);
    }

}
