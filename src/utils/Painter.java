package utils;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import utils.backend_logic.State;
import utils.front_end_logic.Colorful_Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static utils.consts.*;

// Handle some basic painting
public class Painter {
    //Single Thread to guarantee order of execution
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    public void paint_by_Status(Colorful_Rectangle rectangle) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                int status = rectangle.getStatus();
                rectangle.setFill(getFill(status));
                rectangle.setStroke(getStroke(status));

                //Optional blinking
                if (status == THE_SUCCESSFUL_RECT_STATUS) {
                    blink(Arrays.asList(rectangle), 50, 10,
                            THE_FOCUSED_FILL_COLOR, THE_HIGHLIGHTED_FILL_COLOR);
                }
            }
        });

    }

    public void paint_Many_By_Status(List<State> stateList, List<Colorful_Rectangle> r) {
        //Debate-able use of Thread
        //Might not work consistently if Delay time too small
        final Colorful_Rectangle[] rectangle = new Colorful_Rectangle[1];
        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (State s : stateList) {
                    rectangle[0] = r.get(s.getIndex());
                    rectangle[0].setStatus(s.getStatus());
                    rectangle[0].setHeight(s.getHeight());
                    paint_by_Status(rectangle[0]);
                }
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
                new KeyFrame(Duration.millis(delay), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
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
                            }
                        });

                    }
                }));

        //Back to original colors
        blink_Blink.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < original_Color.size(); i++) {
                            x.get(i).setFill(original_Color.get(i));
                        }
                    }
                });

            }
        });
        blink_Blink.setCycleCount(cycle);
        blink_Blink.play();

    }

}
