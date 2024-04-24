package Controller;

import image.BackGroundScene;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.sm.Main;

import java.io.IOException;

public class Login {
    private Stage stage;
    private Scene scene;
    private AnchorPane anchorPane;

    public void switchtoStudenLogin(ActionEvent event) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StudentLogin.fxml"));
            Parent root=fxmlLoader.load();
            stage=(Stage)((Node) event.getSource()).getScene().getWindow();
            BackGroundScene backGroundScene=new BackGroundScene();
            StackPane stackPane=new StackPane(backGroundScene.getBackgroundView(),root);
            scene=new Scene(stackPane);
            stage.setScene(scene);
            stage.show();
    }

    public void Instructor(ActionEvent event) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("InstructorLogin.fxml"));
            Parent root=fxmlLoader.load();
            stage=(Stage)((Node) event.getSource()).getScene().getWindow();
            BackGroundScene backGroundScene=new BackGroundScene();
            StackPane stackPane=new StackPane(backGroundScene.getBackgroundView(),root);
            scene=new Scene(stackPane);
            stage.setScene(scene);
            stage.show();
    }
}
