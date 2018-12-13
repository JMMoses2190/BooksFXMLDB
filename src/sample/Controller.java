package sample;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
  final String SELECT_QUERY =
      "SELECT authorID, firstName, lastName FROM authors";


    @FXML
    private TextFlow bookID;

    @FXML
    private TextField enterFirstName;

    @FXML
    private Label lblFiName;

    @FXML
    private TextField enterLastName;

    @FXML
    private Label lblLaName;

    //Code currently displays as entry into the database
    @FXML
    void handleString(ActionEvent event) {
      String INSERT_QUERY = String.format("INSERT INTO AUTHORS(firstName, lastName)"
          + "VALUES ('%s', '%s')", enterFirstName.getText(), enterLastName.getText());
      connectAndExecute(INSERT_QUERY);

      System.out.println("Entry Added!");

    }
  @FXML
  private Button newEntry;


  @FXML
  void newString(ActionEvent event) {

  }


  //connects to database
  private void connectAndExecute(String query) {
    try (
        Connection connection = DriverManager.getConnection(
            DATABASE_URL, "deitel", "deitel");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SELECT_QUERY))
    {

      // display query results
      resultSet.next();

      System.out.println(resultSet.getString(2));

    } // AutoCloseable objects' close methods are called now
    catch (SQLException sqlException)
    {
      sqlException.printStackTrace();
    }

    catch ( Exception ex){
      System.out.println("Some other Exception");
    }
  }
}



