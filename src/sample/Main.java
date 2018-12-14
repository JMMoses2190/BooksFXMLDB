package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

  //Starts the fxml file
  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    primaryStage.setTitle("Books Database");
    Scene scene = new Scene(root, 800, 600, Color.BLACK);
    primaryStage.setScene(scene);
    primaryStage.show();
  }


  public static void main(String[] args) {
    launch(args);
  }
}
