package Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URISyntaxException;
import javafx.event.ActionEvent;


public class StudentPlatform {
    @FXML
    Label nameLabel;
    public void displayName(String id)
    {
        nameLabel.setText("Welcome "+id+" !");
    }
    public void calendar(){}
}