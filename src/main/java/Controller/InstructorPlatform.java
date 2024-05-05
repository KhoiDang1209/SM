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

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
        Desktop.getDesktop().browse(new URI("https://scontent.xx.fbcdn.net/v/t1.15752-9/439991678_1153287282488064_1536089699606818016_n.png?stp=dst-png_p403x403&_nc_cat=109&ccb=1-7&_nc_sid=5f2048&_nc_ohc=ypb147wFEwsQ7kNvgF5WAYe&_nc_ad=z-m&_nc_cid=0&_nc_ht=scontent.xx&oh=03_Q7cD1QGRRLVC0bG-KLVW3HMtQjxT1fiX8ArD3i_5pXyWTeK3CQ&oe=665ED36F"));
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

    public void StudentList(ActionEvent event) throws IOException {
        loadScene("StudentList.fxml", event);
    }

    public void GradingLec (ActionEvent event) throws IOException {
        loadScene("Grades.fxml", event);
    }

    public void AnnounceLec (ActionEvent event) throws IOException {
        loadScene("Announce.fxml", event);
    }

    public void ExamDateStu (ActionEvent event) throws IOException {
        loadScene("ExamDateStu.fxml", event);
    }

    public void Personal(ActionEvent event) throws IOException {
        loadScene("Lecinfo.fxml", event);
    }

}
