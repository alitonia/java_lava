package sample;

//TODO: Optimize painting
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import jdk.nashorn.internal.objects.annotations.Getter;
import utils.*;
import utils.front_end_logic.Array_Controller;
import utils.front_end_logic.Colorful_Rectangle;
import utils.front_end_logic.Log;

import java.util.Collection;

import static utils.consts.*;

public class Controller {

    private final Log my_Log = new Log();
    private final History_Manager historyManager = new History_Manager();
    private Generator generator = new Generator();
    private Array_Controller array_controller = new Array_Controller(this);

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
    void start_running(ActionEvent event) {
        my_Log.print("Start button clicked!\n" +
                "Now running!\n");
        array_controller.setStatus(0, SOMEWHAT_SPECIAL_RECT_STATUS);
    }

    @FXML
    void one_step_forward(ActionEvent event) {
        my_Log.print("Next button pressed:\n" +
                "Go to next action\n");
    }

    @FXML
    void pause_running(ActionEvent event) {
        my_Log.print("Pause button pressed:\n" +
                "Stop action\n");

    }

    @FXML
    void one_step_backward(ActionEvent event) {
        my_Log.print("Backward button pressed:\n" +
                "Go to previous action\n");
    }

    @FXML
    void back_to_start(ActionEvent event) {
        my_Log.print("Reset button pressed:\n" +
                "Back to top\n");

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
                "Generating new random values");

        //Get parameters of rectangles
        array_controller.make(NUMBER_OF_RECTANGLE);
        //TODO: This should make request to History_Manager, then Manager change flag
    }

    public void paint_Board() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                clean_Board();
                for (Colorful_Rectangle r: array_controller.getColorful_rectangles()){
                    visual_board.getChildren().add( r);
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

}


