package Controller.CourseLec;

import Controller.DatabaseConnection;
import Controller.InstructorPlatform;
import image.BackGroundScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.sm.Main;

import java.io.IOException;
import java.sql.*;

import static Controller.Login.username;

public class CourseLec {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label annoCourse;
    @FXML
    private TableView<CourseLecRecord> courseLecTable;
    @FXML
    private TableColumn<CourseLecRecord, String> couidCol;
    @FXML
    private TableColumn<CourseLecRecord, String> counameCol;
    @FXML
    private TableColumn<CourseLecRecord, Integer> creCol;
    @FXML
    private TableColumn<CourseLecRecord, String> roomCol;
    @FXML
    private TableColumn<CourseLecRecord, Date> sdayCol;
    @FXML
    private TableColumn<CourseLecRecord, Date> edayCol;
    @FXML
    private TableColumn<CourseLecRecord, Time> stimeCol;
    @FXML
    private TableColumn<CourseLecRecord, Time> etimeCol;
    @FXML
    private TableColumn<CourseLecRecord, String> dowCol;
    public void setAnnoCourse(int n) {
        this.annoCourse.setText("There are " + n + " courses that you taught");
    }
    public void initialize() throws SQLException {
        couidCol.setCellValueFactory(new PropertyValueFactory<CourseLecRecord, String>("courseID"));
        counameCol.setCellValueFactory(new PropertyValueFactory<CourseLecRecord,String>("courseName"));
        creCol.setCellValueFactory(new PropertyValueFactory<CourseLecRecord,Integer>("credit"));
        roomCol.setCellValueFactory(new PropertyValueFactory<CourseLecRecord,String>("room"));
        sdayCol.setCellValueFactory(new PropertyValueFactory<CourseLecRecord,Date>("sdate"));
        edayCol.setCellValueFactory(new PropertyValueFactory<CourseLecRecord,Date>("edate"));
        stimeCol.setCellValueFactory(new PropertyValueFactory<CourseLecRecord,Time>("stime"));
        etimeCol.setCellValueFactory(new PropertyValueFactory<CourseLecRecord,Time>("etime"));
        dowCol.setCellValueFactory(new PropertyValueFactory<CourseLecRecord,String>("dow"));
        Connection cn = DatabaseConnection.getConnection();
        if(cn!=null)
        {
            String query = "SELECT c.CourseID,c.CourseName,c.Credit,Room,c.Start_Day,c.End_Day,c.Start_Time,c.End_Time,c.Day_of_Week FROM Course as c WHERE c.LecturerID=?";
            try (PreparedStatement ps = cn.prepareStatement(query)){
                ps.setString(1,username);
                try(ResultSet rs = ps.executeQuery())
                {
                    ObservableList<CourseLecRecord> courseLecRecords= FXCollections.observableArrayList();
                    while (rs.next())
                    {
                        String courseID = rs.getString("CourseID");
                        String courseName = rs.getString("CourseName");
                        int credit = rs.getInt("Credit");
                        String room = rs.getString("Room");
                        Date sdate = rs.getDate("Start_Day");
                        Date edate = rs.getDate("End_Day");
                        Time stime = rs.getTime("Start_Time");
                        Time etime = rs.getTime("End_Time");
                        String dow = rs.getString("Day_of_Week");
                        courseLecRecords.add(new CourseLecRecord(courseID,courseName,credit,room,sdate,edate,stime,etime,dow));
                    }
                    courseLecTable.setItems(courseLecRecords);
                }
                String queryCount="SELECT COUNT(*) as TotalCourses FROM Course WHERE LecturerID = ?";
                try(PreparedStatement preparedStatement=cn.prepareStatement(queryCount)){
                    preparedStatement.setString(1,username);
                    try(ResultSet rs = preparedStatement.executeQuery())
                    {
                        while (rs.next())
                        {
                            int totalCourses = rs.getInt("TotalCourses");
                            setAnnoCourse(totalCourses);
                        }
                    }
                }
            }
        }
    }
    public void Back(ActionEvent event) throws IOException {
        FXMLLoader Back = new FXMLLoader(Main.class.getResource("InstructorPlatform.fxml"));
        root = Back.load();
        InstructorPlatform instructorPlatform=Back.getController();
        instructorPlatform.displayInsName(username);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        BackGroundScene backGroundScene = new BackGroundScene();
        StackPane stackPane = new StackPane(backGroundScene.getBackgroundView(), root);
        scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.show();
    }
}
