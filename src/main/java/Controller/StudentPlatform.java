package Controller;

import image.BackGroundScene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.sm.Main;
import java.io.IOException;


public class StudentPlatform {
    @FXML
    Label nameLabel;
    Stage stage;
    Scene scene;

    public void displayStuName(String id)
    {
        nameLabel.setText("Welcome "+id+" !");
    }

    private void loadScene(String fxmlFile, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlFile));
        Parent root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        BackGroundScene backGroundScene = new BackGroundScene();
        StackPane stackPane = new StackPane(backGroundScene.getBackgroundView(), root);
        scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.show();
    }

    public void Stuinfo(ActionEvent event) throws IOException {
        loadScene("Stuinfo.fxml", event);
    }

    public void Tuition(ActionEvent event) throws IOException {
        loadScene("Tuition.fxml", event);
    }

    public void Grades (ActionEvent event) throws IOException {
        loadScene("Grades.fxml", event);
    }

    public void Register (ActionEvent event) throws IOException {
        loadScene("Register.fxml", event);
    }

    public void ExamDateStu (ActionEvent event) throws IOException {
        loadScene("ExamDateStu.fxml", event);
    }

    public void Calendar (ActionEvent event) throws IOException {
        loadScene("TimeTable.fxml", event);
    }
}
