package Controller;

import Controller.CourseLec.CourseLec;
import image.BackGroundScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.sm.Main;

import java.io.IOException;

import static Controller.Login.username;

public class InstructorPlatform {
    @FXML
    Label nameLabel;
    @FXML
    Button Course;

    private Stage stage;
    private Scene scene;
    private Parent root;
    public void displayInsName(String id)
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

    public void CourseLecinfo(ActionEvent event) throws IOException {
        loadScene("CourseLec.fxml", event);
    }

    public void TimetableLec(ActionEvent event) throws IOException {
        loadScene("Tuition.fxml", event);
    }

    public void GradingLec (ActionEvent event) throws IOException {
        loadScene("Grades.fxml", event);
    }

    public void AnnouceLec (ActionEvent event) throws IOException {
        loadScene("Register.fxml", event);
    }

    public void ExamDateStu (ActionEvent event) throws IOException {
        loadScene("ExamDateStu.fxml", event);
    }

    public void Personal(ActionEvent event) throws IOException {
        loadScene("Calendar.fxml", event);
    }

}
