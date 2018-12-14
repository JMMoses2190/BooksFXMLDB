package sample;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;

public class Controller {

  private static int recordNumber = 1;

  final String DATABASE_URL = "jdbc:derby:lib\\books";

  //Intializes delete Entry button
  @FXML
  private Button deleteEntry;

  //Initializes Name labels
  @FXML
  private Label lblFirst;
  @FXML
  private Label lblLast;
  @FXML
  private Label lblFiName;
  @FXML
  private Label lblLaName;

  //Initializes BookID Texflow
  @FXML
  private TextFlow bookID;

  //Initializes TextFields
  @FXML
  private TextField enterFirstName;
  @FXML
  private TextField enterLastName;


  //Code takes text from text fields and adds it to the database
  @FXML
  void handleString(ActionEvent event) {
    String INSERT_QUERY = String.format("INSERT INTO AUTHORS(firstName, lastName)"
        + "VALUES ('%s', '%s')", enterFirstName.getText(), enterLastName.getText());
    connectAndExecute(INSERT_QUERY);

    System.out.println("Entry Added!");

  }

  //Code adds functionality for Delete Entry button
  @FXML
  void deleteEntry(ActionEvent event) {
    String DELETE_QUERY = String.format("DELETE FROM AUTHORS WHERE FIRSTNAME = '%s' AND "
        + "LASTNAME = '%s'", lblFirst.getText(), lblLast.getText());

    connectAndExecute(DELETE_QUERY);

    System.out.println("Entry Deleted");
  }


  //Adds functionality to Next Entry button and cycles though the Database showing the various names
  @FXML
  void nextEntry(ActionEvent event) {

    final String SELECT_QUERY = " SELECT authorID, firstName, lastName FROM authors";
    recordNumber++;

    Properties prop = new Properties();

    try (FileInputStream in = new FileInputStream("dir\\db.properties")) {
      prop.load(in);
    } catch (FileNotFoundException fileEx) {
      fileEx.printStackTrace();
    } catch (IOException ioFile) {
      ioFile.printStackTrace();
    }

    try (

        Connection connection = DriverManager.getConnection(
            DATABASE_URL, "deitel", "deitel");
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery(SELECT_QUERY)) {
      if (resultSet.next()) {
        resultSet.absolute(recordNumber);
      }

      if (resultSet.next()) {
        lblFirst.setText(resultSet.getString(2));
        lblLast.setText(resultSet.getString(3));
      } else {
        resultSet.first();
        recordNumber = 1;
        lblFirst.setText(resultSet.getString(2));
        lblLast.setText(resultSet.getString(3));

      }


    } catch (SQLException sqlFile) {
      sqlFile.printStackTrace();
    }
  }

  //Updates the database
  @FXML
  void updateDatabase(ActionEvent event) {
    String UPDATE_QUERY = String.format("UPDATE AUTHORS SET FIRSTNAME = '%s', LASTNAME = '%s' "
            + "WHERE FIRSTNAME = '%s' AND LASTNAME = '%s'", enterFirstName.getText(),
        enterLastName.getText(),
        lblFirst.getText(), lblLast.getText());

    connectAndExecute(UPDATE_QUERY);
    System.out.println("Database Updated!");

  }


  //connects to database
  private void connectAndExecute(String query) {

    Properties prop = new Properties();
    try (FileInputStream in = new FileInputStream("dir\\db.properties")) {
      prop.load(in);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    String username = prop.getProperty("jdbc.username");
    String password = prop.getProperty("jdbc.password");

    try (Connection connect = DriverManager.getConnection(DATABASE_URL, username, password);
        Statement state = connect.createStatement();) {
      try {
        state.execute(query);
      } catch (Exception exe) {
        exe.printStackTrace();
      }

    } catch (SQLException sqlExe) {
      sqlExe.printStackTrace();

    }
  }
}



