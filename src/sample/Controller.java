package sample;

//TODO: Optimize painting


import javafx.application.Platform;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import jdk.nashorn.internal.objects.annotations.Getter;

import org.reactfx.EventStreams;
import org.reactfx.Subscription;
import utils.*;
import utils.backend_logic.SearchingUtils;
import utils.backend_logic.State;
import utils.backend_logic.my_Queue;
import utils.front_end_logic.Array_Controller;
import utils.front_end_logic.Colorful_Rectangle;
import utils.front_end_logic.Log;
import utils.front_end_logic.Painter;


import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static utils.consts.*;

public class Controller {

    private final Log my_Log = new Log();
    private final History_Manager historyManager = new History_Manager();
    private Array_Controller array_controller = new Array_Controller(this);
    private int execution_Status = SEQUENTIAL_MODE;
    private SearchingUtils generator = new SearchingUtils();
    private my_Queue history_Manager = new my_Queue();
    private Painter painter = new Painter();
    private Subscription playing_Stream;
    //    Executor executor = Executors.newFixedThreadPool(MAX_EXECUTIONERS);
    @FXML
    private Button cancel_button;

    @FXML
    private Button start_button;

    @FXML
    private Pane visual_board;

    @FXML
    private Button step_forward_button;

    @FXML
    private Button step_backward_button;

    @FXML
    private Button pause_button;

    @FXML
    private Button reset_button;

    @FXML
    private Pane button_box;

    @FXML
    private Button randomize_button;

    @FXML
    private ComboBox mode_choice;

    @FXML
    void start_running(ActionEvent event) {
        my_Log.print("Start button clicked!\n" +
                "Now running!\n");
        //Disable bug-able button :)
        pause_button.setDisable(false);
        step_forward_button.setDisable(true);
        step_backward_button.setDisable(true);
        start_button.setDisable(true);
        randomize_button.setDisable(true);
        mode_choice.setDisable(true);

        //Main event
        if (history_Manager.getQueue_Length() == 0) {
            my_Log.print("No instance created!");
        } else {
            if (playing_Stream == null) {
                playing_Stream = EventStreams.ticks(Duration.ofMillis(DELAY_MILIS_PER_RUN))
                        .supplyCompletionStage(
                                () -> CompletableFuture.supplyAsync(() -> history_Manager.get_Next_Step()))
                        .await()

                        .subscribe(v -> {
                            if (v != null) {
                                painter.paint_by_Many_Status(v, array_controller.getColorful_rectangles());
                            } else {
                                playing_Stream.unsubscribe();
                                playing_Stream = null;
                                pause_button.setDisable(true);
                                step_forward_button.setDisable(true);
                                step_backward_button.setDisable(false);
                                reset_button.setDisable(false);
                                randomize_button.setDisable(false);
                                mode_choice.setDisable(false);
                            }
                        });
            } else {
                my_Log.print("Already playing");
            }
        }
    }

    @FXML
    void pause_running(ActionEvent event) {
        my_Log.print("Pause button pressed:\n" +
                "Stop action\n");
        if (playing_Stream != null) {
            playing_Stream.unsubscribe();
            playing_Stream = null;
        } else {
            my_Log.print("No stream playing");
        }
        pause_button.setDisable(true);
        if (history_Manager.isEnding() == false) {
            step_forward_button.setDisable(false);
            start_button.setDisable(false);
        }
        if (history_Manager.isHead() == false) {
            step_backward_button.setDisable(false);
            reset_button.setDisable(false);
        }
        randomize_button.setDisable(false);
        mode_choice.setDisable(false);
    }

    @FXML
    void one_step_forward(ActionEvent event) {
        my_Log.print("Next button pressed:\n" +
                "Go to next action\n");
        List<State> now_State = history_Manager.get_Next_Step();
        if (now_State != null) {
            painter.paint_by_Many_Status(now_State, array_controller.getColorful_rectangles());
        } else {
            my_Log.print("Reach end of steps!");
        }
        if (history_Manager.isEnding() == true) {
            step_forward_button.setDisable(true);
            start_button.setDisable(true);
        }
        else {
            step_backward_button.setDisable(false);
            reset_button.setDisable(false);
        }
    }

    @FXML
    void one_step_backward(ActionEvent event) {
        my_Log.print("Backward button pressed:\n" +
                "Go to previous action\n");
        List<State> now_State = history_Manager.get_Previous_Step();
        if (now_State != null) {
            painter.paint_by_Many_Status(now_State, array_controller.getColorful_rectangles());
        } else {
            my_Log.print("Reach head of steps!");
        }
        if (history_Manager.isEnding() == false) {
            step_forward_button.setDisable(false);
            start_button.setDisable(false);
        }
        if (history_Manager.isHead() == true){
            reset_button.setDisable(true);
            step_backward_button.setDisable(true);
        }
    }

    @FXML
    void back_to_start(ActionEvent event) {
        my_Log.print("Reset button pressed:\n" +
                "Back to top\n");
        pause_running(event);
        List<State> now_State = history_Manager.back_to_Start();

        painter.paint_by_Many_Status(now_State, array_controller.getColorful_rectangles());
        step_backward_button.setDisable(true);
        reset_button.setDisable(true);
        start_button.setDisable(false);
        step_forward_button.setDisable(false);
    }

    @FXML
    void cancel_program(ActionEvent event) {
        System.out.println("Cancel button pressed:\n" +
                "Now exiting\n");
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void generate_random(ActionEvent event) {
        my_Log.print("Random button pressed:\n" +
                "Generating new values");
        //Clean previous operation
        pause_running(event);
        execution_Status = mode_choice.getItems().indexOf(mode_choice.getValue());

        //Get parameters of rectangles
        if (execution_Status == SEQUENTIAL_MODE) {
            array_controller.make(NUMBER_OF_RECTANGLE);
            my_Log.print("Mode: " + SEQUENTIAL);

            //For generator
            generator.setInternal_List(array_controller.get_List_Double_format());
            my_Queue.setInternal_List(array_controller.get_List_State_format());
            my_Queue.setOrigin_List(array_controller.get_List_State_format());

            //Current target is the 6th element of histogram
            generator.Sequential_Search(
                    array_controller.get_List_Double_format().get(5),
                    array_controller.get_List_Double_format());
            my_Queue.print();

        } else if (execution_Status == BINARY_MODE) {
            array_controller.make_Ordered(NUMBER_OF_RECTANGLE);
            my_Log.print("Mode: " + BINARY);

            //
        } else if (execution_Status == A_STAR_MODE) {
            array_controller.make_2D(NUMBER_OF_RECTANGLE_HORIZONTAL, NUMBER_OF_RECTANGLE_VERTICAL);
            my_Log.print("Mode: " + A_Star);

            //
        } else {
            System.out.println("Error parsing choice!");
        }
        history_Manager.back_to_Start();
        //enable buttons:
        start_button.setDisable(false);
        step_forward_button.setDisable(false);
        step_backward_button.setDisable(true);
        reset_button.setDisable(true);

    }

    public void paint_Board(ObservableList<Colorful_Rectangle> colorful_rectangles) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                my_Log.print("Painting...");
                visual_board.getChildren().addAll(colorful_rectangles);
                my_Log.print("Values of rectangles:");
                for (Colorful_Rectangle r : colorful_rectangles) {
                    my_Log.print(r.toString());
                }
            }
        });
    }


    public void clean_Board() {
        visual_board.getChildren().clear();
    }

    @Getter
    public Pane getVisual_board() {
        return visual_board;
    }

    public ComboBox getMode_choice() {
        return mode_choice;
    }
}


