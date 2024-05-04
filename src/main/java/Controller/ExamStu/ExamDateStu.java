package Controller.ExamStu;

import Controller.DatabaseConnection;
import Controller.StudentPlatform;
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

public class ExamDateStu {
    @FXML
    private Label stuidLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label dobLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Label majorLabel;
    @FXML
    private Label classLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label levelLabel;
    @FXML
    private TableView<ExamRecord> examTable;
    @FXML
    private TableColumn<ExamRecord, Date> examdateCol;
    @FXML
    private TableColumn<ExamRecord, String> courseidCol;
    @FXML
    private TableColumn<ExamRecord, Time> examtimeCol;
    @FXML
    private TableColumn<ExamRecord, String> examroomCol;
    @FXML
    private TableColumn<ExamRecord, String> coursenameCol;
    private Stage stage;
    private Scene scene;
    private Parent root;

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
    public void setStuidLabel(String stuid) {
        stuidLabel.setText("StudentID: "+stuid);
    }
    public void setNameLabel(String name) {
        nameLabel.setText("Name: "+name);
    }
    public void setDobLabel(Date dob) {
        dobLabel.setText("DOB: "+dob);
    }
    public void setGenderLabel(String gender) {
        genderLabel.setText("Gender: "+gender);
    }
    public void setMajorLabel(String major) {
        majorLabel.setText("Major: "+major);
    }
    public void setClassLabel(String classes) {
        classLabel.setText("Class: "+classes);
    }
    public void setEmailLabel(String email) {
        emailLabel.setText("Email: "+email);
    }
    public void setLevelLabel(String level) {
        levelLabel.setText("Level: "+level);
    }
    public void initialize() throws SQLException {
        Connection cn = DatabaseConnection.getConnection();
        courseidCol.setCellValueFactory(new PropertyValueFactory<>("courseID"));
        coursenameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        examdateCol.setCellValueFactory(new PropertyValueFactory<>("examDate"));
        examtimeCol.setCellValueFactory(new PropertyValueFactory<>("examTime"));
        examroomCol.setCellValueFactory(new PropertyValueFactory<>("examRoom"));
        if(cn!=null)
        {
            String query="SELECT s.StudentID, s.Name, s.DateOfBirth, s.Gender, s.Major, s.Class, s.Email, s.Level, ex.CourseID, c.CourseName ,ex.ExamDate,ex.ExamTime, ex.ExamRoom FROM Student as s, Exam as ex, Enroll as er, Course as c WHERE s.StudentID=? and s.StudentID=er.StudentID and er.CourseID=ex.CourseID and er.CourseID=c.CourseID";
            try (PreparedStatement pstmt = cn.prepareStatement(query)) {
                pstmt.setString(1,username );
                try (ResultSet rs = pstmt.executeQuery()) {
                    ObservableList<ExamRecord> examRecord = FXCollections.observableArrayList();
                    while (rs.next()) {
                        String studentID = rs.getString("StudentID");
                        String Name = rs.getString("Name");
                        Date DateOfBirth = rs.getDate("DateOfBirth");
                        String Gender = rs.getString("Gender");
                        String Major = rs.getString("Major");
                        String Class = rs.getString("Class");
                        String Email = rs.getString("Email");
                        String Level = rs.getString("Level");
                        String CourseID = rs.getString("CourseID");
                        String CourseName = rs.getString("CourseName");
                        Date ExamDate = rs.getDate("ExamDate");
                        Time ExamTime = rs.getTime("ExamTime");
                        String ExamRoom = rs.getString("ExamRoom");
                        setStuidLabel(studentID);
                        setNameLabel(Name);
                        setDobLabel(DateOfBirth);
                        setGenderLabel(Gender);
                        setMajorLabel(Major);
                        setClassLabel(Class);
                        setEmailLabel(Email);
                        setLevelLabel(Level);
                        examRecord.add(new ExamRecord(CourseID,CourseName ,ExamDate, ExamTime, ExamRoom));
                    }
                    examTable.setItems(examRecord);
                }
            }
        }
    }
}
