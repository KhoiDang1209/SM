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
    @FXML
    private TableColumn<TimeTableRecord, Time> LabStartTimeCol;
    @FXML
    private TableColumn<TimeTableRecord, Time> LabEndTimeCol;
    @FXML
    private TableColumn<TimeTableRecord, String> LabDayCol;

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
        LabStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("LabStartTime"));
        LabEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("LabEndTime"));
        LabDayCol.setCellValueFactory(new PropertyValueFactory<>("LabDay"));

        if(cn!=null)
        {
            String query = "SELECT s.StudentID, c.Day_of_Week, c.CourseName, c.Start_Time, c.End_Time, c.Room, c.CourseID, L.Start_Time as Lab_Start_Time, L.End_Time as Lab_End_Time, L.Day_of_Week as Lab_Day " +
                    "FROM Student s " +
                    "INNER JOIN Enroll er ON s.StudentID = er.StudentID " +
                    "INNER JOIN Course c ON er.CourseID = c.CourseID " +
                    "LEFT JOIN Lab L ON L.CourseID = c.CourseID " +
                    "WHERE s.StudentID = ?";
            try (PreparedStatement pstmt = cn.prepareStatement(query)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    ObservableList<TimeTableRecord> timeTableRecords = FXCollections.observableArrayList();
                    while (rs.next()) {
                        String dayOfWeek = rs.getString("Day_of_Week");
                        String courseID = rs.getString("CourseID");
                        String courseName = rs.getString("CourseName");
                        Time startTime = rs.getTime("Start_Time");
                        Time endTime = rs.getTime("End_Time");
                        String room = rs.getString("Room");
                        Time labStartTime = rs.getTime("Lab_Start_Time");
                        Time labEndTime = rs.getTime("Lab_End_Time");
                        String labDay = rs.getString("Lab_Day");
                        timeTableRecords.add(new TimeTableRecord(courseID, courseName, startTime, endTime, room, dayOfWeek, labStartTime, labEndTime, labDay));
                    }
                    TimeTable.setItems(timeTableRecords);
                }
            }
        }
    }
}