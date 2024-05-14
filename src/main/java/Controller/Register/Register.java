package Controller.Register;

import java.sql.PreparedStatement;
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
    private TableView<Controller.Register.RegisterRec> TableView;
    @FXML
    private TableView<Controller.Register.RegisterRec> table2;
    @FXML
    private TableColumn<Controller.Register.RegisterRec, String> CourseIDColumn;
    @FXML
    private TableColumn<Controller.Register.RegisterRec, String> CourseNameColumn;
    @FXML
    private TableColumn<Controller.Register.RegisterRec, Integer> CreditColumn;
    @FXML
    private TableColumn<Controller.Register.RegisterRec, String> RoomColumn;
    @FXML
    private TableColumn<Controller.Register.RegisterRec, String> LecturerNameColumn;
    @FXML
    private TableColumn<Controller.Register.RegisterRec, String> DayColumn;
    @FXML
    private TableColumn<Controller.Register.RegisterRec, Time> StartColumn;
    @FXML
    private TableColumn<Controller.Register.RegisterRec, Time> EndColumn;
    @FXML
    private TableColumn<Controller.Register.RegisterRec, String> CourseIDColumn1;
    @FXML
    private TableColumn<Controller.Register.RegisterRec, String> CourseNameColumn1;
    @FXML
    private TableColumn<Controller.Register.RegisterRec, Integer> CreditColumn1;
    @FXML
    private TableColumn<Controller.Register.RegisterRec, String> RoomColumn1;
    @FXML
    private TableColumn<Controller.Register.RegisterRec, String> LecturerNameColumn1;
    @FXML
    private TableColumn<Controller.Register.RegisterRec, String> DayColumn1;
    @FXML
    private TableColumn<Controller.Register.RegisterRec, Time> StartColumn1;
    @FXML
    private TableColumn<Controller.Register.RegisterRec, Time> EndColumn1;
    @FXML
    private TableColumn<Controller.Register.RegisterRec, CheckBox> SelectColumn;
    @FXML
    private TableColumn<Controller.Register.RegisterRec, CheckBox> DeleteColumn;
    @FXML
    private TextField filterTextField;
    @FXML
    ObservableList<Controller.Register.RegisterRec> RegisterRecObservableList = FXCollections.observableArrayList();//bang 1
    @FXML
    ObservableList<Controller.Register.RegisterRec> dataListRemove = FXCollections.observableArrayList(); // bang 2
    @FXML
    ObservableList<Controller.Register.RegisterRec> dataListRemove2 = FXCollections.observableArrayList();// chon de them
    @FXML
    ObservableList<Controller.Register.RegisterRec> dataListDelete = FXCollections.observableArrayList();// chon de xoa

    @Override
    public void initialize(URL url, ResourceBundle resource) {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = DatabaseConnection.getConnection();


        try {
            PreparedStatement stmt = connectDB.prepareCall("{CALL GetUnregisteredCourses(?)}");
            stmt.setString(1, username);
            ResultSet queryOutput = stmt.executeQuery();

            while(queryOutput.next()){
                String queryCourseID = queryOutput.getString("CourseID");
                String queryCourseName = queryOutput.getString("CourseName");
                int queryCredit = queryOutput.getInt("Credit");
                String queryRoom = queryOutput.getString("Room");
                String queryLecturerName = queryOutput.getString("LecturerName");

                String queryDay = queryOutput.getString("Day_of_Week");
                Time queryStart = queryOutput.getTime("Start_Time");
                Time queryEnd = queryOutput.getTime("End_Time");
                RegisterRecObservableList.add(new Controller.Register.RegisterRec(queryCourseID, queryCourseName, queryCredit, queryRoom, queryLecturerName, queryDay, queryStart, queryEnd));
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
            DeleteColumn.setCellValueFactory(new PropertyValueFactory<>("Delete"));

            CourseIDColumn1.setCellValueFactory(new PropertyValueFactory<>("CourseID"));
            CourseNameColumn1.setCellValueFactory(new PropertyValueFactory<>("CourseName"));
            CreditColumn1.setCellValueFactory(new PropertyValueFactory<>("Credit"));
            RoomColumn1.setCellValueFactory(new PropertyValueFactory<>("Room"));
            LecturerNameColumn1.setCellValueFactory(new PropertyValueFactory<>("LecturerName"));

            EndColumn1.setCellValueFactory(new PropertyValueFactory<>("End"));
            DayColumn1.setCellValueFactory(new PropertyValueFactory<>("Day"));
            StartColumn1.setCellValueFactory(new PropertyValueFactory<>("Start"));

            TableView.setItems(RegisterRecObservableList);

            FilteredList<Controller.Register.RegisterRec> filteredData = new FilteredList<>(RegisterRecObservableList, b -> true);
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

            SortedList<Controller.Register.RegisterRec> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(TableView.comparatorProperty());
            TableView.setItems(sortedData);

// =============================               TABLE 2                ==========================================================
            PreparedStatement stmt2 = connectDB.prepareCall("{CALL GetRegisteredCourses(?)}");
            stmt2.setString(1, username);
            ResultSet queryOutput2 = stmt2.executeQuery();

            while(queryOutput2.next()){
                String queryCourseID = queryOutput2.getString("CourseID");
                String queryCourseName = queryOutput2.getString("CourseName");
                int queryCredit = queryOutput2.getInt("Credit");
                String queryRoom = queryOutput2.getString("Room");
                String queryLecturerName = queryOutput2.getString("LecturerName");

                String queryDay = queryOutput2.getString("Day_of_Week");
                Time queryStart = queryOutput2.getTime("Start_Time");
                Time queryEnd = queryOutput2.getTime("End_Time");
                dataListRemove.add(new Controller.Register.RegisterRec(queryCourseID, queryCourseName, queryCredit, queryRoom, queryLecturerName, queryDay, queryStart, queryEnd));
            }

            table2.setItems(dataListRemove);
            //===============================================================================
        }catch (SQLException e){
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }

    @FXML
    private void addToDatabase(ActionEvent event){

        ObservableList<Controller.Register.RegisterRec> selectedItems = TableView.getSelectionModel().getSelectedItems();
        table2.getItems().addAll(selectedItems);
        RegisterRecObservableList.removeAll(selectedItems);

        for ( Controller.Register.RegisterRec bean : RegisterRecObservableList){
            if(bean.getSelect().isSelected()){
                dataListRemove2.add(bean);
                dataListRemove.add(bean);
            }
        }

        table2.setItems(dataListRemove);
        RegisterRecObservableList.removeAll(dataListRemove2);

        Connection connectDB = DatabaseConnection.getConnection();
        try {
            String insertQuery = "INSERT INTO Registered_Courses (ID_User, ID_Course ) VALUES (?, ?)";
            PreparedStatement preparedStatement = connectDB.prepareStatement(insertQuery);
            for (Controller.Register.RegisterRec bean : dataListRemove2) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, bean.getCourseID());


                preparedStatement.executeUpdate();
                preparedStatement.clearParameters();

            }
            preparedStatement.close();
        } catch (SQLException e){
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        try{
            String insertQuery2 = "INSERT INTO Enroll (StudentID, CourseID, Progress, Midterm, Final, Overall, OverallCharacter, Semester) VALUES (?, ?, 0, 0, 0, 0, 'F', 'I-2024')";
            PreparedStatement preparedStatement2 = connectDB.prepareStatement(insertQuery2);
            for (Controller.Register.RegisterRec bean : dataListRemove2) {
                preparedStatement2.setString(1, username);
                preparedStatement2.setString(2, bean.getCourseID());
                preparedStatement2.executeUpdate();
            }
        }
        catch (SQLException e){
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }

    @FXML
    private void Delete(ActionEvent event) {
        ObservableList<Controller.Register.RegisterRec> selectedItems2 = table2.getSelectionModel().getSelectedItems();
        TableView.getItems().addAll(selectedItems2);
        dataListRemove.removeAll(selectedItems2);

        for ( Controller.Register.RegisterRec bean : dataListRemove){
            if(bean.getDelete().isSelected()){
//                dataListRemove2.add(bean);
//                dataListRemove.add(bean);
                RegisterRecObservableList.add(bean);
                dataListDelete.add(bean);
            }
        }
        TableView.setItems(RegisterRecObservableList);

        dataListRemove.removeAll(dataListDelete);
        table2.setItems(dataListRemove);

        // ============== DATABASE ===============
        Connection connectDB = DatabaseConnection.getConnection();
        String query = "delete from Registered_Courses where ID_Course = ? and ID_User = ?";
        String query2 = "delete from Enroll where StudentID = ? and CourseID = ?";

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(query);
            for (Controller.Register.RegisterRec bean : dataListDelete) {
                preparedStatement.setString(1, bean.getCourseID());
                preparedStatement.setString(2, username);
                preparedStatement.executeUpdate();
                preparedStatement.clearParameters();
            }
            preparedStatement.close();
        } catch (SQLException e){
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        try {
            PreparedStatement preparedStatement2 = connectDB.prepareStatement(query2);
            for (Controller.Register.RegisterRec bean : dataListDelete) {
                preparedStatement2.setString(2, bean.getCourseID());
                preparedStatement2.setString(1, username);
                preparedStatement2.executeUpdate();
                preparedStatement2.clearParameters();
            }
            preparedStatement2.close();
        } catch (SQLException e){
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }
    // xu ly database
}

