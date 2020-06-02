package components;

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
import utils.Log;
import utils.Painter;
import utils.backend_logic.*;
import utils.front_end_logic.Colorful_Rectangle;
import utils.front_end_logic.Visual_Factory;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import static utils.constants.*;

public class Controller {

    private String execution_Mode = SEQUENTIAL_SEARCH_MODE;
    private Subscription play_Stream;

    private final Log my_Log = new Log();
    private final Visual_Factory visual_Factory = new Visual_Factory(this);
    private final History_Manager history_Manager = new History_Manager();
    private final Painter painter = new Painter(this);

    private final Searching_Generator search_Generator = new Searching_Generator();
    private final A_Path_Finding graph_search_Generator = new A_Path_Finding();
    private final Sorting_Generator sort_Generator = new Sorting_Generator();

    private final ExecutorService button_Executor = Executors.newSingleThreadExecutor();
//    private final ExecutorService graphic_Executor = Executors.newSingleThreadExecutor();

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
            reset_Button.setDisable(true);
                }
        );
        long delay = getDelay(execution_Mode);

        // Run continuously until pause or
        // end of queue(next return null)
        if (history_Manager.is_Empty()) {
            my_Log.print("No instance created!");
        } else {
            my_Log.print("Play stream created");
            if (play_Stream == null) {
                play_Stream = EventStreams.ticks(Duration.ofMillis(delay))
                        .supplyCompletionStage(
                                () -> CompletableFuture.supplyAsync(history_Manager::get_Next))
                        .await()

                        .subscribe(v -> {
                            if (v != null) {
                                painter.paint_Many_By_Status(v, visual_Factory.get_Colorful_Rectangles());
                            } else {
                                button_Executor.execute(() -> {
                                            play_Stream.unsubscribe();
                                            play_Stream = null;
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

        if (play_Stream != null) {
            play_Stream.unsubscribe();
            play_Stream = null;
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

        List<State_Blob> now_State_Blob = history_Manager.get_Next();
        if (now_State_Blob != null) {
            painter.paint_Many_By_Status(now_State_Blob, visual_Factory.get_Colorful_Rectangles());
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

        List<State_Blob> now_State_Blob = history_Manager.get_Previous();
        if (now_State_Blob != null) {
            painter.paint_Many_By_Status(now_State_Blob, visual_Factory.get_Colorful_Rectangles());
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
        List<State_Blob> original_State_Blob = history_Manager.get_Origin_List();
        System.out.println("This " + original_State_Blob.size());
        painter.paint_Many_By_Status(original_State_Blob, visual_Factory.get_Colorful_Rectangles());

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
        execution_Mode = mode_Choice.getValue();

        // make visual objects based on choice
        switch (execution_Mode) {
            case SEQUENTIAL_SEARCH_MODE:
                generate_Sequential();
                break;

            case BINARY_SEARCH_MODE:
                generate_Binary();
                break;

            case A_STAR_PATH_FINDING_MODE:
                generate_A_Star();
                break;

            case BUBBLE_SORT_MODE:
                generate_Bubble();
                break;

            case SELECTION_SORT_MODE:
                generate_Selection();
                break;

            case MERGE_SORT_MODE:
                generate_Merge();
                break;

            case QUICK_SORT_MODE:
                generate_Quick();
                break;

            case INSERTION_SORT_MODE:
                generate_Insertion();
                break;

            default:
                System.out.println("Error parsing choice!");
                break;
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
        my_Log.print("Mode: " + SEQUENTIAL_SEARCH_MODE);
        visual_Factory.make_Histogram(NUMBER_OF_HISTOGRAM_SEARCH_RECTANGLE);

        // set context for history manager
        history_Manager.set_Origin_List(visual_Factory.get_List_State_Format());

        // set target as the x-th element of histogram
        // with constraint as having distance 0.3*length away from start
        // to make animation look more exciting
        Colorful_Rectangle target = visual_Factory.get_Colorful_Rectangles().get(
                ThreadLocalRandom.current().nextInt(
                        visual_Factory.getLength() - MINIMUM_HISTOGRAM_TRAVERSING_DISTANCE)
                        + MINIMUM_HISTOGRAM_TRAVERSING_DISTANCE
        );

        //set target_line
        target_Line.setStartY(target.getY());
        target_Line.setEndY(target.getY());
        target_Line.setVisible(true);

        if (TARGET_LINE_TO_FRONT) {
            Platform.runLater(() -> target_Line.toFront());
        }

        //generate steps to history Manager
        search_Generator.sequential_Search(
                target.getHeight(),
                visual_Factory.get_List_Double_Format(),
                history_Manager
        );
        my_Log.print("Done");
    }


    private void generate_Binary() {
        my_Log.print("Mode: " + BINARY_SEARCH_MODE);
        visual_Factory.make_Ordered_Histogram(NUMBER_OF_HISTOGRAM_SEARCH_RECTANGLE);

        // set context for history manager
        history_Manager.set_Origin_List(visual_Factory.get_List_State_Format());

        // set target as the x-th element of histogram
        Colorful_Rectangle target = visual_Factory.get_Colorful_Rectangles().get(
                ThreadLocalRandom.current().nextInt(visual_Factory.getLength()));

        //set target-line
        target_Line.setStartY(target.getY());
        target_Line.setEndY(target.getY());
        target_Line.setVisible(true);

        if (TARGET_LINE_TO_FRONT) {
            Platform.runLater(() -> target_Line.toFront());
        }

        //generate steps to history Manager
        search_Generator.binary_Search(
                target.getHeight(),
                visual_Factory.get_List_Double_Format(),
                history_Manager
        );
        my_Log.print("Done");
    }


    private void generate_A_Star() {
        //no need for target_line
        target_Line.setVisible(false);
        my_Log.print("Mode: " + A_STAR_PATH_FINDING_MODE);
        visual_Factory.make_Map(NUMBER_OF_RECTANGLE_X_AXIS, NUMBER_OF_RECTANGLE_Y_AXIS);

        // set context for history manager
        // Height doesn't change so don't need to repaint height
        history_Manager.set_Origin_List(visual_Factory.get_List_State_Format(NO_REPAINT_HEIGHT_SIGNAL));
        System.out.println(visual_Factory.get_List_Node_Format().toString());

        //generator generate something here
        //currently no engine for start
        graph_search_Generator.find_Path(NUMBER_OF_RECTANGLE_X_AXIS, NUMBER_OF_RECTANGLE_Y_AXIS,
                visual_Factory.get_List_Node_Format(), history_Manager);

        my_Log.print("Done");
    }


    private void generate_Bubble() {
        target_Line.setVisible(false);
        my_Log.print("Mode: " + BUBBLE_SORT_MODE);
        visual_Factory.make_Histogram(NUMBER_OF_HISTOGRAM_SLOW_SORT_RECTANGLE);

        history_Manager.set_Origin_List(visual_Factory.get_List_State_Format());
        System.out.println(visual_Factory.get_List_Node_Format().toString());

        sort_Generator.Bubble_Sort(visual_Factory.get_List_Double_Format(), history_Manager);

        my_Log.print("Done");
    }


    private void generate_Selection() {
        target_Line.setVisible(false);
        my_Log.print("Mode: " + SELECTION_SORT_MODE);
        visual_Factory.make_Histogram(NUMBER_OF_HISTOGRAM_SLOW_SORT_RECTANGLE);

        history_Manager.set_Origin_List(visual_Factory.get_List_State_Format());
        System.out.println(visual_Factory.get_List_Node_Format().toString());

        sort_Generator.Selection_Sort(visual_Factory.get_List_Double_Format(), history_Manager);

        my_Log.print("Done");
    }


    private void generate_Merge() {
        target_Line.setVisible(false);
        my_Log.print("Mode: " + SELECTION_SORT_MODE);
        visual_Factory.make_Histogram(NUMBER_OF_HISTOGRAM_FAST_SORT_RECTANGLE);

        history_Manager.set_Origin_List(visual_Factory.get_List_State_Format());
        System.out.println(visual_Factory.get_List_Node_Format().toString());

        sort_Generator.merge_Sort(
                visual_Factory.get_List_Double_Format(),
                0, visual_Factory.get_List_Double_Format().size() - 1,
                history_Manager);

        my_Log.print("Done");
    }


    private void generate_Quick() {
        target_Line.setVisible(false);
        my_Log.print("Mode: " + SELECTION_SORT_MODE);
        visual_Factory.make_Histogram(NUMBER_OF_HISTOGRAM_FAST_SORT_RECTANGLE);

        history_Manager.set_Origin_List(visual_Factory.get_List_State_Format());
        System.out.println(visual_Factory.get_List_Node_Format().toString());

        sort_Generator.quick_Sort(
                visual_Factory.get_List_Double_Format(),
                0, visual_Factory.get_List_Double_Format().size() - 1,
                history_Manager);

        my_Log.print("Done");
    }


    private void generate_Insertion() {
        target_Line.setVisible(false);
        my_Log.print("Mode: " + SELECTION_SORT_MODE);
        visual_Factory.make_Histogram(NUMBER_OF_HISTOGRAM_SLOW_SORT_RECTANGLE);

        history_Manager.set_Origin_List(visual_Factory.get_List_State_Format());
        System.out.println(visual_Factory.get_List_Node_Format().toString());

        sort_Generator.insertion_Sort(visual_Factory.get_List_Double_Format(), history_Manager);

        my_Log.print("Done");
    }


    public void paint_Board(ObservableList<Colorful_Rectangle> colorful_rectangles) {
        Platform.runLater(() -> {
            my_Log.print("Painting board...");
            visual_Board.getChildren().addAll(colorful_rectangles);
            my_Log.print("Done");
        });
    }


    public void clean_Board() {
        my_Log.print("Cleaning board...");
        visual_Board.getChildren().clear();
        visual_Board.getChildren().add(target_Line);
        target_Line.setVisible(false);
        my_Log.print("Done");

    }

    @Getter
    public Pane get_Visual_Board() {
        return visual_Board;
    }

    public ComboBox<String> get_Mode_Choice() {
        return mode_Choice;
    }
}


