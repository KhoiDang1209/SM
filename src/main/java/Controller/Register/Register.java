package Controller.Register;

import Controller.DatabaseConnection;
import Controller.StudentPlatform;
import image.BackGroundScene;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.sm.Main;

import javax.security.auth.callback.Callback;
import javax.swing.*;

import java.awt.geom.QuadCurve2D;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
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
    private TableColumn<RegisterRec, String> LecturerNameColumn;
    @FXML
    private TableColumn<RegisterRec, String> DayColumn;
    @FXML
    private TableColumn<RegisterRec, Time> StartColumn;
    @FXML
    private TableColumn<RegisterRec, Time> EndColumn;
    @FXML
    private TableColumn<RegisterRec, CheckBox> SelectColumn;
    @FXML
    private TextField filterTextField;
    @FXML

    ObservableList<RegisterRec> RegisterRecObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resource) {





        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = DatabaseConnection.getConnection();

        String RegisterViewQuery = "SELECT c.CourseID, c.CourseName, c.Credit, c.Room, L.LecturerName,  c.Day_of_Week, c.Start_Time, c.End_Time FROM Course AS c \n" +
                "                INNER JOIN Lecturer AS L ON L.LecturerID = c.LecturerID";

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(RegisterViewQuery);

            while(queryOutput.next()){
                String queryCourseID = queryOutput.getString("CourseID");
                String queryCourseName = queryOutput.getString("CourseName");
                int queryCredit = queryOutput.getInt("Credit");
                String queryRoom = queryOutput.getString("Room");
                String queryLecturerName = queryOutput.getString("LecturerName");

                String queryDay = queryOutput.getString("Day_of_Week");
                Time queryStart = queryOutput.getTime("Start_Time");
                Time queryEnd = queryOutput.getTime("End_Time");
                


                RegisterRecObservableList.add(new RegisterRec(queryCourseID, queryCourseName, queryCredit, queryRoom, queryLecturerName, queryDay, queryStart, queryEnd));
//
            }


            CourseIDColumn.setCellValueFactory(new PropertyValueFactory<>("CourseID"));
            CourseNameColumn.setCellValueFactory(new PropertyValueFactory<>("CourseName"));
            CreditColumn.setCellValueFactory(new PropertyValueFactory<>("Credit"));
            RoomColumn.setCellValueFactory(new PropertyValueFactory<>("Room"));
            LecturerNameColumn.setCellValueFactory(new PropertyValueFactory<>("LecturerName"));

            EndColumn.setCellValueFactory(new PropertyValueFactory<>("End"));
            DayColumn.setCellValueFactory(new PropertyValueFactory<>("Day"));
            StartColumn.setCellValueFactory(new PropertyValueFactory<>("Start"));
            SelectColumn.setCellValueFactory(new PropertyValueFactory<>("Select"));



            TableView.setItems(RegisterRecObservableList);
            TableView.getColumns().add(SelectColumn);
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
