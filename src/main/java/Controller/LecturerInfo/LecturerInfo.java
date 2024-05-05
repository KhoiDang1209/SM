package Controller.LecturerInfo;

import Controller.InstructorPlatform;
import image.BackGroundScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.sm.Main;

import java.io.IOException;

import static Controller.Login.username;

public class LecturerInfo {
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

}

