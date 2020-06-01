package utils;

import components.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import utils.backend_logic.State_Blob;
import utils.front_end_logic.Colorful_Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static utils.constants.*;

// Handle some basic painting
public class Painter {
    //Single Thread to guarantee order of execution
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private Controller controller;
    private final Log my_Log = new Log();


    public Painter() {
    }

    public Painter(Controller c) {
        this.controller = c;
    }


    public synchronized void paint_by_Status(Colorful_Rectangle rectangle) {
        executor.execute(() -> {

            int status = rectangle.getStatus();
            my_Log.print("Original fill: " + rectangle.getFill() + " to" + getFill(status));
            my_Log.print("Original stroke: " + rectangle.getStroke() + " to" + getStroke(status));

            rectangle.setFill(getFill(status));
            rectangle.setStroke(getStroke(status));

            //Optional blinking
//                if (status == THE_SUCCESSFUL_RECT_STATUS) {
//                    blink(Collections.singletonList(rectangle), 15, 2,
//                            THE_FOCUSED_FILL_COLOR, THE_SECOND_SPOTLIGHT_STROKE_COLOR);
//                }
        });
    }

    private synchronized void paint_By_Height(Colorful_Rectangle rectangle, double height) {
        executor.execute(() -> {

            if (height != NO_REPAINT_HEIGHT_SIGNAL) {
                Pane my_Stage = controller.get_Visual_Board();
                my_Log.print("Original height: " + rectangle.getHeight() + " to" + height);

                rectangle.setHeight(height);
                // because the Oxy axis is to the upper left of the screen
                // so drawing rectangles is a bit weird
                rectangle.setY(my_Stage.getLayoutY() + (my_Stage.getHeight() - rectangle.getHeight()));
            }
        });


    }

    public synchronized void paint_Many_By_Status(List<State_Blob> state_Blob_List, List<Colorful_Rectangle> r) {
        //Debate-able use of Thread
        //Might not work consistently if Delay time too small
        final Colorful_Rectangle[] rectangle = new Colorful_Rectangle[1];
        executor.execute(() -> {
            for (State_Blob s : state_Blob_List) {
                rectangle[0] = r.get(s.getIndex());
                rectangle[0].setStatus(s.getStatus());

                paint_By_Height(rectangle[0], s.getHeight());
                paint_by_Status(rectangle[0]);
            }
        });
    }

    public void blink(List<Colorful_Rectangle> x, long delay, int cycle, Color... colors) {
        //store original colors
        List<Color> original_Color = new ArrayList<>();
        for (Colorful_Rectangle rectangle : x) {
            original_Color.add((Color) rectangle.getFill());
        }

        //Rotate between chosen colors
        //TODO: unify timeline for color in one rotation
        Timeline blink_Blink = new Timeline(
                new KeyFrame(Duration.millis(delay), event -> executor.execute(() -> {
                    for (Color color : colors) {
                        for (Colorful_Rectangle rectangle : x) {
                            rectangle.setFill(color);
                        }
                        try {
                            Thread.sleep(delay);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                })));

        //Back to original colors
        blink_Blink.setOnFinished(event -> executor.execute(() -> {
            for (int i = 0; i < original_Color.size(); i++) {
                x.get(i).setFill(original_Color.get(i));
            }
        }));
        blink_Blink.setCycleCount(cycle);
        blink_Blink.play();

    }

}
