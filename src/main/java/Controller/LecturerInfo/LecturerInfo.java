package Controller.LecturerInfo;

import Controller.DatabaseConnection;
import Controller.GradesStudent.Grades;
import Controller.InstructorPlatform;
import Controller.Login;
import image.BackGroundScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

public class LecturerInfo implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label lecturerEmailLabel;

    @FXML
    private Label lecturerFieldLabel;

    @FXML
    private Label lecturerIDLabel;

    @FXML
    private Label lecturerJoinDateLabel;

    @FXML
    private Label lecturerNameLabel;

    @FXML
    private Label lecturerPhoneLabel;

    @FXML
    void Back(ActionEvent event) throws IOException {
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

    void setLecturerIDLabel(String lecturerID) {
        lecturerIDLabel.setText(lecturerID);
    }
    void setLecturerNameLabel(String lecturerName) {
        lecturerNameLabel.setText(lecturerName);
    }
    void setLecturerEmailLabel(String lecturerEmail) {
        lecturerEmailLabel.setText(lecturerEmail);
    }
    void setLecturerPhoneLabel(String lecturerPhone) {
        lecturerPhoneLabel.setText(lecturerPhone);
    }
    void setLecturerFieldLabel(String lecturerField) {
        lecturerFieldLabel.setText(lecturerField);
    }
    void setLecturerJoinDateLabel(Date lecturerJoinDate) {
        lecturerJoinDateLabel.setText(lecturerJoinDate + "");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connectionNow = new DatabaseConnection();
        Connection connectDB = null;
        Statement statement = null;
        ResultSet queryOutput = null;

        connectDB = DatabaseConnection.getConnection();

        if (connectDB != null) {
            String gradesViewQuery = "SELECT * FROM Lecturer WHERE LecturerID = '" + Login.username + "'";

            try {
                statement = connectDB.createStatement();
                queryOutput = statement.executeQuery(gradesViewQuery);

                while (queryOutput.next()) {
                    String queryLecturerID = queryOutput.getString("LecturerID");
                    String queryName = queryOutput.getString("LecturerName");
                    String queryEmail = queryOutput.getString("LecturerEmail");
                    String queryPhone = queryOutput.getString("LecturerPhone");
                    String queryField = queryOutput.getString("Field");
                    Date queryJoinDate = queryOutput.getDate("Join_Date");

                    setLecturerIDLabel(queryLecturerID);
                    setLecturerNameLabel(queryName);
                    setLecturerEmailLabel(queryEmail);
                    setLecturerPhoneLabel(queryPhone);
                    setLecturerFieldLabel(queryField);
                    setLecturerJoinDateLabel(queryJoinDate);
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

