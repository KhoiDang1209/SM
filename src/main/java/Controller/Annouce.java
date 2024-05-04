package Controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class Annouce {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private ComboBox courseBox;
    @FXML
    private ComboBox emailBox;
    @FXML
    private TableView<Annouce> tableView;
}
