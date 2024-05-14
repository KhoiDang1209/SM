package Controller;

import Controller.Admin.Admin;
import image.BackGroundScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.sm.Main;

import java.io.IOException;
import java.sql.*;
public class Login {

    private Stage stage;
    private Scene scene;
    @FXML
    TextField userTextField;
    @FXML
    PasswordField passTextField;

    public static String username;
    public static String password;
    public void Login(ActionEvent event) throws IOException {
        username = userTextField.getText();
        password = passTextField.getText();
        if (username.equals("1") && password.equals("1")) {
            openStudentPlatform(username, event);
        } else if (username.equals("admin") && password.equals("123")) {
            openAdminPlatform(username, event);
        } else {
            try (Connection cn = DatabaseConnection.getConnection()) {
                if (cn != null) {
                    // Kiểm tra trong bảng Student
                    String queryStudent = "SELECT StudentID FROM Student WHERE StudentID=? AND stu_pass=?";
                    try (PreparedStatement pstmtStudent = cn.prepareStatement(queryStudent)) {
                        pstmtStudent.setString(1, username);
                        pstmtStudent.setString(2, password);
                        try (ResultSet rsStudent = pstmtStudent.executeQuery()) {
                            if (rsStudent.next()) {
                                // Xử lý đăng nhập của student
                                openStudentPlatform(username, event);
                                return;
                            }
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    // Kiểm tra trong bảng Lecturer
                    String queryLecturer = "SELECT LecturerID FROM Lecturer WHERE LecturerID=? AND lec_pass=?";
                    try (PreparedStatement pstmtLecturer = cn.prepareStatement(queryLecturer)) {
                        pstmtLecturer.setString(1, username);
                        pstmtLecturer.setString(2, password);
                        try (ResultSet rsLecturer = pstmtLecturer.executeQuery()) {
                            if (rsLecturer.next()) {
                                // Xử lý đăng nhập của lecturer
                                openInstructorPlatform(username, event);
                                return;
                            }
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Nếu không phải student hoặc lecturer, hiển thị thông báo lỗi
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password. Please try again.");
            alert.showAndWait();
        }
    }

    private void openStudentPlatform(String username, ActionEvent event) throws IOException {
        FXMLLoader studentLoader = new FXMLLoader(Main.class.getResource("StudentPlatform.fxml"));
        Parent root = studentLoader.load();
        StudentPlatform studentController = studentLoader.getController();
        studentController.displayStuName(username);
        openPlatform(root, event);
    }

    private void openInstructorPlatform(String username, ActionEvent event) throws IOException {
        FXMLLoader instructorLoader = new FXMLLoader(Main.class.getResource("InstructorPlatform.fxml"));
        Parent root = instructorLoader.load();
        InstructorPlatform instructorController = instructorLoader.getController();
        instructorController.displayInsName(username);
        openPlatform(root, event);
    }
    private void openAdminPlatform(String username, ActionEvent event) throws IOException {
        FXMLLoader adminLoader = new FXMLLoader(Main.class.getResource("Admin.fxml"));
        Parent root = adminLoader.load();
        Admin admin = adminLoader.getController();
        admin.displayInsName(username);
        openPlatform(root, event);
    }
    private void openPlatform(Parent root, ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        BackGroundScene backgroundScene = new BackGroundScene();
        StackPane stackPane = new StackPane(backgroundScene.getBackgroundView(), root);
        scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.show();
    }

}

