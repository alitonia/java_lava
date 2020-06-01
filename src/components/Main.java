package components;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

//TODO: Change name of window
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Initialize
        Parent root;

//        Parent root = FXMLLoader.load(getClass().getResource("screens/Mini_Screen.fxml"));

        root = FXMLLoader.load(getClass().getResource("Large_Screen.fxml"));


        // the address is not changed when rename parent directory @@ --> be careful
        root.getStylesheets().add("components/styles/Plum_Style.css");

        primaryStage.setTitle("Hello Visualization");
        primaryStage.getIcons().add(new Image("pictures/javascript.jpeg"));

        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);


        //Exit program when out
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
