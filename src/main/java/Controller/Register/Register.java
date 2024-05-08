package Controller.Register;

import Controller.DatabaseConnection;
import Controller.StudentPlatform;
import image.BackGroundScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.sm.Main;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Controller.Login.username;

public class Register implements Initializable {
    @FXML
    Label nameLabel;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void displayStuName(String id)
    {
        nameLabel.setText("Welcome "+id+" !");
    }
    public void Back(ActionEvent event) throws IOException {
        FXMLLoader Back = new FXMLLoader(Main.class.getResource("StudentPlatform.fxml"));
        root = Back.load();
        StudentPlatform studentController = Back.getController();
        studentController.displayStuName(username);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        BackGroundScene backGroundScene = new BackGroundScene();
        StackPane stackPane = new StackPane(backGroundScene.getBackgroundView(), root);
        scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private TableView<RegisterRec> TableView;
    @FXML
    private TableColumn<RegisterRec, String> CourseIDColumn;
    @FXML
    private TableColumn<RegisterRec, String> CourseNameColumn;
    @FXML
    private TableColumn<RegisterRec, Integer> CreditColumn;
    @FXML
    private TableColumn<RegisterRec, String> RoomColumn;
    @FXML
    private TextField filterTextField;

    ObservableList<RegisterRec> RegisterRecObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resource) {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = DatabaseConnection.getConnection();

        String RegisterViewQuery = "SELECT c.CourseID, c.CourseName, c.Credit, c.Room FROM Course AS c";

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(RegisterViewQuery);

            while(queryOutput.next()){
                String queryCourseID = queryOutput.getString("CourseID");
                String queryCourseName = queryOutput.getString("CourseName");
                int queryCredit = queryOutput.getInt("Credit");
                String queryRoom = queryOutput.getString("Room");


                RegisterRecObservableList.add(new RegisterRec(queryCourseID, queryCourseName, queryCredit, queryRoom));

            }


            CourseIDColumn.setCellValueFactory(new PropertyValueFactory<>("CourseID"));
            CourseNameColumn.setCellValueFactory(new PropertyValueFactory<>("CourseName"));
            CreditColumn.setCellValueFactory(new PropertyValueFactory<>("Credit"));
            RoomColumn.setCellValueFactory(new PropertyValueFactory<>("Room"));

            TableView.setItems(RegisterRecObservableList);

            FilteredList<RegisterRec> filteredData = new FilteredList<>(RegisterRecObservableList, b -> true);
            filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(RegisterRec ->{
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String search = newValue.toLowerCase();

                    if (RegisterRec.getCourseName().toLowerCase().indexOf(search) > -1) {
                        return true;
                    } else {
                        return false;
                    }
                });
            });

            SortedList<RegisterRec> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(TableView.comparatorProperty());
            TableView.setItems(sortedData);

        }catch (SQLException e){
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }
}
