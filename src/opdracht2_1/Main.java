package opdracht2_1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Registratie.fxml"));
        primaryStage.setTitle("OOAD 2_1");
        primaryStage.setScene(new Scene(root, 700, 280));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
