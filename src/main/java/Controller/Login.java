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
        if (username.equals("admin") && password.equals("123")) {
            openAdminPlatform(username, event);
        } else {
            try (Connection cn = DatabaseConnection.getConnection()) {
                if (cn != null) {
                    String queryUser="SELECT Role from [User] where UserName=? and UserPassword=?";
                    try(PreparedStatement ps = cn.prepareStatement(queryUser)) {
                        ps.setString(1, username);
                        ps.setString(2, password);
                        ResultSet rs = ps.executeQuery();
                        if(rs==null)
                        {

                        }
                        while(rs.next()) {
                            String role=rs.getString("Role");
                            if(role.equals("Student")){
                                openStudentPlatform(username, event);
                            }
                            else openAdminPlatform(username, event);
                        }
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

