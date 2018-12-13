package sample;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {
  private static int recordNumber = 1;

  final String DATABASE_URL = "jdbc:derby:C:\\Users\\Jorda\\IdeaProjects\\BooksDBFX\\lib\\books";

  @FXML


  private TextField enterFirstName;

  @FXML
  private TextField enterLastName;


  @FXML
  private void handleString(ActionEvent event) {
    String INSERT_QUERY = String.format("INSERT INTO AUTHORS(firstName, lastName)"
        + "VALUES ('%s', '%s')", enterFirstName.getText(), enterLastName.getText());
    connectAndExecute(INSERT_QUERY);

    System.out.println("Entry Added!");

  }

  private void connectAndExecute(String query) {
    Properties prop = new Properties();
    try (FileInputStream in = new FileInputStream("dir/db.properties")) {
      prop.load(in);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    String uname = prop.getProperty("jdbc.username");
    String pword = prop.getProperty("jdbc.password");

    try (Connection connection = DriverManager.getConnection(DATABASE_URL, uname, pword);
        Statement statement = connection.createStatement()) {
      try {
        statement.execute(query);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    } catch (SQLException sqlEx) {
      sqlEx.printStackTrace();
    }
  }
}



