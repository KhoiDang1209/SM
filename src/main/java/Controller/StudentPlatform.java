package Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URISyntaxException;
import javafx.event.ActionEvent;
import javafx.stage.Stage;


public class StudentPlatform {
    @FXML
    Label nameLabel;
    public void displayName(String id)
    {
        nameLabel.setText("Welcome "+id+" !");
    }
    public void calendar(){}

    private Stage stage;
    private Scene scene;
    private Parent root;
    public void switchToPersonalInfo(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Stuinfo.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToRegister(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToTimeTable(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Calendar.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToTuition(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Tuition.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToExamDate(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ExamDate.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToGrades(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Grades.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}