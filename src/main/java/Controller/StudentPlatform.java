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

    public void displayStuName(String id)
    {
        nameLabel.setText("Welcome "+id+" !");
    }
    private Stage stage;
    private Scene scene;
    private Parent root;
    public void Stuinfo(ActionEvent event) throws IOException{
        FXMLLoader Stuinfo = new FXMLLoader(Main.class.getResource("Stuinfo.fxml"));
        root = Stuinfo.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        BackGroundScene backGroundScene = new BackGroundScene();
        StackPane stackPane = new StackPane(backGroundScene.getBackgroundView(), root);
        scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.show();
    }
    public void Tuition(ActionEvent event) throws IOException{
        FXMLLoader Tuition = new FXMLLoader(Main.class.getResource("Tuition.fxml"));
        root = Tuition.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        BackGroundScene backGroundScene = new BackGroundScene();
        StackPane stackPane = new StackPane(backGroundScene.getBackgroundView(), root);
        scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.show();
    }

    public void Grades (ActionEvent event) throws IOException{
        FXMLLoader Grades = new FXMLLoader(Main.class.getResource("Grades.fxml"));
        root = Grades.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        BackGroundScene backGroundScene = new BackGroundScene();
        StackPane stackPane = new StackPane(backGroundScene.getBackgroundView(), root);
        scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.show();
    }

    public void Register (ActionEvent event) throws IOException{
        FXMLLoader Register = new FXMLLoader(Main.class.getResource("Register.fxml"));
        root = Register.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        BackGroundScene backGroundScene = new BackGroundScene();
        StackPane stackPane = new StackPane(backGroundScene.getBackgroundView(), root);
        scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.show();
    }

    public void ExamDateStu (ActionEvent event) throws IOException{
        FXMLLoader ExamDate = new FXMLLoader(Main.class.getResource("ExamDateStu.fxml"));
        root = ExamDate.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        BackGroundScene backGroundScene = new BackGroundScene();
        StackPane stackPane = new StackPane(backGroundScene.getBackgroundView(), root);
        scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.show();
    }

    public void Calendar (ActionEvent event) throws IOException{
        FXMLLoader Calendar = new FXMLLoader(Main.class.getResource("Calendar.fxml"));
        root = Calendar.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        BackGroundScene backGroundScene = new BackGroundScene();
        StackPane stackPane = new StackPane(backGroundScene.getBackgroundView(), root);
        scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.show();
    }

}