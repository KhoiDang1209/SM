package Controller;

import image.BackGroundScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.sm.Main;

import java.io.IOException;

public class StudentLogin {
    private Stage stage;
    private Scene scene;
    @FXML
    TextField userTextField;
    @FXML
    TextField passTextField;

    public void Back(ActionEvent event) throws IOException {
        FXMLLoader login = new FXMLLoader(Main.class.getResource("Login.fxml"));
        Parent root = login.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        BackGroundScene backGroundScene = new BackGroundScene();
        StackPane stackPane = new StackPane(backGroundScene.getBackgroundView(), root);
        scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.show();
    }

    public void Login(ActionEvent event) throws IOException {
        String username = userTextField.getText();
        String password = passTextField.getText();
        if (username.equals("ITCSIU22266") && password.equals("1") || username.equals("ITCSIU22252") && password.equals("1") ) {
            FXMLLoader studentplatform = new FXMLLoader(Main.class.getResource("StudentPlatform.fxml"));
            Parent root = studentplatform.load();
            StudentPlatform studentPlatform = studentplatform.getController();
            studentPlatform.displayName(username);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            BackGroundScene backGroundScene = new BackGroundScene();
            StackPane stackPane = new StackPane(backGroundScene.getBackgroundView(), root);
            scene = new Scene(stackPane);
            stage.setScene(scene);
            stage.show();
        } else {
            // Nếu username hoặc password không đúng, hiển thị thông báo lỗi hoặc thực hiện hành động khác
            // Ví dụ: Hiển thị một cửa sổ thông báo
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password. Please try again.");
            alert.showAndWait();

        }
    }
}
