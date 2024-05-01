package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class InstructorPlatform {
    @FXML
    Label nameLabel;

    public void displayInsName(String id)
    {
        nameLabel.setText("Welcome "+id+" !");
    }

}
