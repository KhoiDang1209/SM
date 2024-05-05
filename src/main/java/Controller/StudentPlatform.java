package Controller;

import image.BackGroundScene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.sm.Main;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class StudentPlatform {
    @FXML
    Label nameLabel;
    Stage stage;
    Scene scene;

    private Hyperlink hyperlink;
    public void displayStuName(String id)
    {
        nameLabel.setText("Welcome "+id+" !");
    }

    public void openLink1(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://edusoftweb.hcmiu.edu.vn/default.aspx?page=chitietthongtin&id=1430"));
    }
    public void openLink2(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://edusoftweb.hcmiu.edu.vn/Upload/Lich%20nam%20hoc%202023%202024%20-%20Signed%20(3).pdf"));
    }
    public void openLink3(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://edusoftweb.hcmiu.edu.vn/default.aspx?page=chitietthongtin&id=1429"));
    }
    public void openLink4(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://edusoftweb.hcmiu.edu.vn/default.aspx?page=chitietthongtin&id=1428"));
    }
    public void openLink5(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://www.youtube.com/shorts/YZEyGq5hExg"));
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
