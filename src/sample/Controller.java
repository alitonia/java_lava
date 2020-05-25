package sample;

//TODO: Optimize painting


import javafx.application.Platform;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import jdk.nashorn.internal.objects.annotations.Getter;

import org.reactfx.EventStreams;
import org.reactfx.Subscription;
import utils.backend_logic.SearchingUtils;
import utils.backend_logic.State;
import utils.backend_logic.history_Manager;
import utils.front_end_logic.Array_Controller;
import utils.front_end_logic.Colorful_Rectangle;
import utils.Log;
import utils.Painter;


import java.time.Duration;
import java.util.List;
import java.util.concurrent.*;

import static utils.consts.*;

public class Controller {

    private int execution_Status = SEQUENTIAL_MODE;
    private Subscription playing_Stream;

    private final Log my_Log = new Log();
    private final Array_Controller array_Controller = new Array_Controller(this);
    private final SearchingUtils generator = new SearchingUtils();
    private final utils.backend_logic.history_Manager history_Manager = new history_Manager();
    private final Painter painter = new Painter();

    private final ExecutorService button_Executor = Executors.newSingleThreadExecutor();
    private final ExecutorService graphic_Executor = Executors.newSingleThreadExecutor();

    @FXML
    private Button cancel_Button;

    @FXML
    private Button start_Button;

    @FXML
    private Pane visual_Board;

    @FXML
    private Button forward_Button;

    @FXML
    private Button backward_Button;

    @FXML
    private Button pause_Button;

    @FXML
    private Button reset_Button;

    @FXML
    private Pane button_Box;

    @FXML
    private Button randomize_Button;

    @FXML
    private ComboBox<String> mode_Choice;

    @FXML
    private Line target_Line;


    @FXML
    void run(ActionEvent event) {
        my_Log.print("Start button clicked!\n" +
                "Now running!\n");

        //Disable bug-able button :)
        button_Executor.execute(() -> {
                    pause_Button.setDisable(false);
                    forward_Button.setDisable(true);
                    backward_Button.setDisable(true);
                    start_Button.setDisable(true);
                    randomize_Button.setDisable(true);
                    mode_Choice.setDisable(true);
                }
        );
        long delay = getDelay(execution_Status);


        // Run continuously until pause or
        // end of queue(next return null)
        if (history_Manager.is_Empty()) {
            my_Log.print("No instance created!");
        } else {
            if (playing_Stream == null) {
                playing_Stream = EventStreams.ticks(Duration.ofMillis(delay))
                        .supplyCompletionStage(
                                () -> CompletableFuture.supplyAsync(history_Manager::get_Next))
                        .await()

                        .subscribe(v -> {
                            if (v != null) {
                                painter.paint_Many_By_Status(v, array_Controller.get_Colorful_Rectangles());
                            } else {
                                button_Executor.execute(() -> {
                                            playing_Stream.unsubscribe();
                                            playing_Stream = null;
                                            pause_Button.setDisable(true);
                                            forward_Button.setDisable(true);
                                            backward_Button.setDisable(false);
                                            reset_Button.setDisable(false);
                                            randomize_Button.setDisable(false);
                                            mode_Choice.setDisable(false);
                                        }
                                );
                            }
                        });
            } else {
                my_Log.print("Already playing");
            }
        }
    }

    @FXML
    void pause(ActionEvent event) {
        my_Log.print("Pause button pressed:\n" +
                "Stop action\n");

        if (playing_Stream != null) {
            playing_Stream.unsubscribe();
            playing_Stream = null;
        } else {
            my_Log.print("No stream playing");
        }

        button_Executor.execute(() -> {
                    pause_Button.setDisable(true);
                    if (!history_Manager.is_Tail()) {
                        forward_Button.setDisable(false);
                        start_Button.setDisable(false);
                    }
                    if (!history_Manager.is_Top()) {
                        backward_Button.setDisable(false);
                        reset_Button.setDisable(false);
                    }
                    randomize_Button.setDisable(false);
                    mode_Choice.setDisable(false);
                }
        );

    }

    @FXML
    void step_Forward(ActionEvent event) {
        my_Log.print("Next button pressed:\n" +
                "Go to next action\n");

        List<State> now_State = history_Manager.get_Next();
        if (now_State != null) {
            painter.paint_Many_By_Status(now_State, array_Controller.get_Colorful_Rectangles());
        } else {
            my_Log.print("Reach end of steps!");
        }

        button_Executor.execute(() -> {
                    if (history_Manager.is_Tail()) {
                        forward_Button.setDisable(true);
                        start_Button.setDisable(true);
                    } else {
                        backward_Button.setDisable(false);
                        reset_Button.setDisable(false);
                    }
                }
        );

    }

    @FXML
    void step_Backward(ActionEvent event) {
        my_Log.print("Backward button pressed:\n" +
                "Go to previous action\n");

        List<State> now_State = history_Manager.get_Previous();
        if (now_State != null) {
            painter.paint_Many_By_Status(now_State, array_Controller.get_Colorful_Rectangles());
        } else {
            my_Log.print("Reach head of steps!");
        }

        button_Executor.execute(() -> {
                    if (!history_Manager.is_Tail()) {
                        forward_Button.setDisable(false);
                        start_Button.setDisable(false);
                    }
                    if (history_Manager.is_Top()) {
                        reset_Button.setDisable(true);
                        backward_Button.setDisable(true);
                    }
                }
        );

    }

    @FXML
    void reset(ActionEvent event) {
        my_Log.print("Reset button pressed:\n" +
                "Back to top\n");

        // pause current process(if any)
        pause(event);

        //restore original state
        history_Manager.reset();
        List<State> original_State = history_Manager.get_Origin_List();
        painter.paint_Many_By_Status(original_State, array_Controller.get_Colorful_Rectangles());

        button_Executor.execute(() -> {
                    backward_Button.setDisable(true);
                    reset_Button.setDisable(true);
                    start_Button.setDisable(false);
                    forward_Button.setDisable(false);
                }
        );

    }

    @FXML
    void cancel(ActionEvent event) {
        System.out.println("Cancel button pressed:\n" + "Now exiting\n");
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void generate_Random(ActionEvent event) {
        my_Log.print("Random button pressed:\n" + "Generating new values");

        // clean previous operation
        pause(event);
        history_Manager.clear();

        // get choice in ComboBox in a complicated way
        execution_Status = mode_Choice.getItems().indexOf(mode_Choice.getValue());

        // make visual objects basad on choice
        if (execution_Status == SEQUENTIAL_MODE) {
            generate_Sequential();

        } else if (execution_Status == BINARY_MODE) {
            generate_Binary();

        } else if (execution_Status == A_STAR_MODE) {
            generate_A_Star();

        } else {
            System.out.println("Error parsing choice!");
        }

        //enable buttons:
        button_Executor.execute(() -> {
                    start_Button.setDisable(false);
                    forward_Button.setDisable(false);
                    backward_Button.setDisable(true);
                    reset_Button.setDisable(true);
                }
        );
    }


    private void generate_Sequential() {
        my_Log.print("Mode: " + SEQUENTIAL);
        array_Controller.make_Histogram(NUMBER_OF_RECTANGLE);

        // set context for history manager
        history_Manager.set_Origin_List(array_Controller.get_List_State_Format());

        // set target as the x-th element of histogram
        // with constraint as having distance 0.3*length away from start
        // to make animation look more exciting
        Colorful_Rectangle target = array_Controller.get_Colorful_Rectangles().get(
                ThreadLocalRandom.current().nextInt(
                        array_Controller.getLength() - MINIMUM_HISTOGRAM_TRAVERSING_DISTANCE)
                        + MINIMUM_HISTOGRAM_TRAVERSING_DISTANCE
        );

        //set target_line
        target_Line.setStartY(target.getY());
        target_Line.setEndY(target.getY());
        target_Line.setVisible(true);

        if (TARGET_LINE_TO_FRONT) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    target_Line.toFront();
                }
            });
        }

        //generate steps to history Manager
        generator.sequential_Search(
                target.getHeight(),
                array_Controller.get_List_Double_Format(),
                history_Manager
        );
    }


    private void generate_Binary() {
        my_Log.print("Mode: " + BINARY);
        array_Controller.make_Ordered_Histogram(NUMBER_OF_RECTANGLE);

        // set context for history manager
        history_Manager.set_Origin_List(array_Controller.get_List_State_Format());

        // set target as the x-th element of histogram
        Colorful_Rectangle target = array_Controller.get_Colorful_Rectangles().get(
                ThreadLocalRandom.current().nextInt(array_Controller.getLength()));

        //set target-line
        target_Line.setStartY(target.getY());
        target_Line.setEndY(target.getY());
        target_Line.setVisible(true);

        if (TARGET_LINE_TO_FRONT) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    target_Line.toFront();
                }
            });
        }

        //generate steps to history Manager
        generator.binary_Search(
                target.getHeight(),
                array_Controller.get_List_Double_Format(),
                history_Manager
        );
    }


    private void generate_A_Star() {
        //no need for target_line
        target_Line.setVisible(false);
        my_Log.print("Mode: " + A_Star);
        array_Controller.make_Map(NUMBER_OF_RECTANGLE_X_AXIS, NUMBER_OF_RECTANGLE_Y_AXIS);

        // set context for history manager
        history_Manager.set_Origin_List(array_Controller.get_List_State_Format());

        //generator generate something here

    }


    public void paint_Board(ObservableList<Colorful_Rectangle> colorful_rectangles) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                my_Log.print("Painting...");
                visual_Board.getChildren().addAll(colorful_rectangles);
                my_Log.print("Values of rectangles:");
                for (Colorful_Rectangle r : colorful_rectangles) {
//                    my_Log.print(r.toString());
                }
            }
        });
    }


    public void clean_Board() {
        visual_Board.getChildren().clear();
        visual_Board.getChildren().add(target_Line);
        target_Line.setVisible(false);
    }

    @Getter
    public Pane get_Visual_Board() {
        return visual_Board;
    }

    public ComboBox<String> get_Mode_Choice() {
        return mode_Choice;
    }
}


