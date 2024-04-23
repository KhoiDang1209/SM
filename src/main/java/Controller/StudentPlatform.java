package Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StudentPlatform {
    @FXML
    Label nameLabel;
    public void displayName(String id)
    {
        nameLabel.setText("Welcome "+id+" !");
    }
}