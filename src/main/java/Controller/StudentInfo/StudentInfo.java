package Controller.StudentInfo;

import Controller.DatabaseConnection;
import Controller.GradesStudent.Grades;
import Controller.GradesStudent.GradesModel;
import Controller.Login;
import Controller.StudentPlatform;
import image.BackGroundScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.sm.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Controller.Login.username;

public class StudentInfo implements Initializable {
    @FXML
    Label nameLabel;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label classLabel;

    @FXML
    private Label dateOfBirthLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label genderLabel;

    @FXML
    private Label levelLabel;

    @FXML
    private Label majorLabel;

    @FXML
    private Label studentInfoLabel;

    @FXML
    private Label studentNameLabel;

    public void setStudentIDLabel(String studentID) {
        studentInfoLabel.setText(studentID);
    }
    public void setStudentNameLabel(String studentName) {
        studentNameLabel.setText(studentName);
    }
    public void setStudentDateofBirth(Date dateofBirth) {
        dateOfBirthLabel.setText(dateofBirth + "");
    }

    public void setGenderLabel(String gender) {
        genderLabel.setText(gender);
    }
    public void setMajorLabel(String major) {
        majorLabel.setText(major);
    }
    public void setClassLabel(String studentClass) {
        classLabel.setText(studentClass);
    }
    public void setEmailLabel(String email) {
        emailLabel.setText(email);
    }
    public void setLevelLabel(String level) {
        levelLabel.setText(level);
    }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connectionNow = new DatabaseConnection();
        Connection connectDB = null;
        Statement statement = null;
        ResultSet queryOutput = null;

        connectDB = DatabaseConnection.getConnection();

        if (connectDB != null) {
            String gradesViewQuery = "SELECT * FROM Student WHERE StudentID = '" + Login.username + "'";

            try {
                statement = connectDB.createStatement();
                queryOutput = statement.executeQuery(gradesViewQuery);

                while (queryOutput.next()) {
                    String queryStudentID = queryOutput.getString("StudentID");
                    String queryName = queryOutput.getString("Name");
                    Date queryDateofBirth = queryOutput.getDate("DateOfBirth");
                    String queryGender = queryOutput.getString("Gender");
                    String queryMajor = queryOutput.getString("Major");
                    String queryClass = queryOutput.getString("Class");
                    String queryEmail = queryOutput.getString("Email");
                    String queryStudentLevel = queryOutput.getString("Level");

                    setStudentIDLabel(queryStudentID);
                    setStudentNameLabel(queryName);
                    setStudentDateofBirth(queryDateofBirth);
                    setGenderLabel(queryGender);
                    setMajorLabel(queryMajor);
                    setClassLabel(queryClass);
                    setEmailLabel(queryEmail);
                    setLevelLabel(queryStudentLevel);
                }

            } catch (SQLException e) {
                Logger.getLogger(Grades.class.getName()).log(Level.SEVERE, null, e);
            } finally {
                try {
                    if (queryOutput != null)
                        queryOutput.close();
                    if (statement != null)
                        statement.close();
                    if (connectDB != null)
                        connectDB.close();
                } catch (SQLException e) {
                    Logger.getLogger(Grades.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        } else {
            Logger.getLogger(Grades.class.getName()).log(Level.SEVERE, "Database connection is null");
        }
    }
}
