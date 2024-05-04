package Controller;

import image.BackGroundScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.sm.Main;

import java.io.IOException;

import static Controller.Login.username;

public class PersonalEmail {
    private Stage stage;
    private Scene scene;
    private Parent root;
    public void Back(ActionEvent event) throws IOException {
        FXMLLoader Back = new FXMLLoader(Main.class.getResource("StudentPlatform.fxml"));
        root = Back.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        BackGroundScene backGroundScene = new BackGroundScene();
        StackPane stackPane = new StackPane(backGroundScene.getBackgroundView(), root);
        StudentPlatform studentController = Back.getController();
        studentController.displayStuName(username);
        scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.show();
    }

}
