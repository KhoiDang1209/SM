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
import javafx.scene.control.PasswordField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.sm.Main;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Controller.Login.password;
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
    @FXML
    private Pane changePassPane;
    @FXML
    private PasswordField current;
    @FXML
    private PasswordField newp;
    @FXML
    private PasswordField firmnew;
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
        changePassPane.setVisible(false);
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
    public void changepass()
    {
        changePassPane.setVisible(true);
    }
    public void confirmpass() throws SQLException {
        String currentpass = current.getText();
        String newpass = newp.getText();
        String firmpass = firmnew.getText();
        if (currentpass.equals(password))
        {
            if (newpass.equals(firmpass))
            {
                Connection cn=DatabaseConnection.getConnection();
                if(cn!=null)
                {
                    String changepass="Update Student Set stu_pass=? where StudentID=?";
                    try(PreparedStatement preparedStatement=cn.prepareStatement(changepass))
                    {
                        preparedStatement.setString(1, newpass);
                        preparedStatement.setString(2, Login.username);
                        preparedStatement.executeUpdate();
                    }
                }
                JOptionPane.showMessageDialog(null, "Password successfully changed!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "New password and confirmed password do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Current password is incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
