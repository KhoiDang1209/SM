package Controller.TimeTable;

import Controller.DatabaseConnection;
import image.BackGroundScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.sm.Main;

import java.io.IOException;
import java.sql.*;

import static Controller.Login.username;

public class TimeTable {
    @FXML
    private TableView<TimeTableRecord> TimeTable;
    @FXML
    private TableColumn<TimeTableRecord, Time> StartTimeCol;
    @FXML
    private TableColumn<TimeTableRecord, String> CourseIDCol;
    @FXML
    private TableColumn<TimeTableRecord, Time> EndTimeCol;
    @FXML
    private TableColumn<TimeTableRecord, String> RoomCol;
    @FXML
    private TableColumn<TimeTableRecord, String> CourseNameCol;
    @FXML
    private TableColumn<TimeTableRecord, String> DayOfWeekCol;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void Back(ActionEvent event) throws IOException {
        FXMLLoader Back = new FXMLLoader(Main.class.getResource("StudentPlatform.fxml"));
        root = Back.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        BackGroundScene backGroundScene = new BackGroundScene();
        StackPane stackPane = new StackPane(backGroundScene.getBackgroundView(), root);
        scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.show();
    }
    public void initialize() throws SQLException {
        Connection cn = DatabaseConnection.getConnection();
        CourseIDCol.setCellValueFactory(new PropertyValueFactory<>("CourseID"));
        CourseNameCol.setCellValueFactory(new PropertyValueFactory<>("CourseName"));
        StartTimeCol.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
        EndTimeCol.setCellValueFactory(new PropertyValueFactory<>("EndTime"));
        RoomCol.setCellValueFactory(new PropertyValueFactory<>("Room"));
        DayOfWeekCol.setCellValueFactory(new PropertyValueFactory<>("Day_of_Week"));
        if(cn!=null)
        {
            String query = "SELECT s.StudentID, c.Day_of_Week, c.CourseName, c.Start_Time, c.End_Time, c.Room, c.CourseID " +
                    "FROM Student s " +
                    "INNER JOIN Enroll er ON s.StudentID = er.StudentID " +
                    "INNER JOIN Course c ON er.CourseID = c.CourseID " +
                    "WHERE s.StudentID = ?";            try (PreparedStatement pstmt = cn.prepareStatement(query)) {
                pstmt.setString(1,username );
                try (ResultSet rs = pstmt.executeQuery()) {
                    ObservableList<TimeTableRecord> TimeTableRecord = FXCollections.observableArrayList();
                    while (rs.next()) {
                        String Day_of_Week = rs.getString("Day_of_Week");
                        String CourseID = rs.getString("CourseID");
                        String CourseName = rs.getString("CourseName");
                        Time StartTime = rs.getTime("Start_Time");
                        Time EndTime = rs.getTime("End_Time");
                        String Room = rs.getString("Room");
                        TimeTableRecord.add(new TimeTableRecord(CourseID,CourseName,StartTime, EndTime, Room, Day_of_Week));
                    }
                    TimeTable.setItems(TimeTableRecord);
                }
            }
        }
    }
}