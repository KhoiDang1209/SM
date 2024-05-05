package Controller.AnnounceEmail;

import Controller.DatabaseConnection;
import Controller.InstructorPlatform;
import image.BackGroundScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.sm.Main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static Controller.Login.username;

public class Announce {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField mesTextfield;
    @FXML
    private ComboBox <String> courseBox;
    @FXML
    private ComboBox <String> nameBox;
    @FXML
    private TableView<EmailRecord> tableView;
    @FXML
    private TableColumn<EmailRecord, String> stuidCol;
    @FXML
    private TableColumn<EmailRecord, String> stunameCol;
    @FXML
    private TableColumn<EmailRecord, String> emailCol;
    private int count=0;
    private String selectedCourse;
    public void initialize() throws SQLException {
        stuidCol.setCellValueFactory(new PropertyValueFactory<EmailRecord, String>("studentID"));
        stunameCol.setCellValueFactory(new PropertyValueFactory<EmailRecord,String>("studentName"));
        emailCol.setCellValueFactory(new PropertyValueFactory<EmailRecord,String>("email"));
        Connection cn = DatabaseConnection.getConnection();
        if(cn!=null)
        {
            String queryCourse="Select CourseID from Course where LecturerID=?";
            try(PreparedStatement ps=cn.prepareStatement(queryCourse))
            {
                ps.setString(1,username);
                try(ResultSet rs=ps.executeQuery())
                {
                    while (rs.next())
                    {
                        String course = rs.getString("CourseID");
                        courseBox.getItems().add(course);
                    }
                }
            }
        }
    }
    public void chooseCourse() throws SQLException {
        Connection cn = DatabaseConnection.getConnection();
        selectedCourse = courseBox.getValue();
        String queryStudent="Select s.Name from Student as s, Enroll as er where er.CourseID=? and s.StudentID=er.StudentID";
        try(PreparedStatement ps=cn.prepareStatement(queryStudent))
        {
            ps.setString(1,selectedCourse);
            try(ResultSet rs=ps.executeQuery())
            {
                while (rs.next())
                {
                    String studentName = rs.getString("Name");
                    nameBox.getItems().add(studentName);
                }
            }
        }
    }
    public void selectStudent() throws SQLException {
        count++;
        Connection cn = DatabaseConnection.getConnection();
        String selectedStudent=nameBox.getValue();
        String queryStudent="SELECT s.StudentID, s.Name, s.Email FROM Student AS s WHERE CAST(s.Name AS NVARCHAR(max)) = ?";
        try(PreparedStatement ps=cn.prepareStatement(queryStudent))
        {
            ps.setString(1,selectedStudent);
            try(ResultSet rs=ps.executeQuery())
            {
                ObservableList<EmailRecord> emailRecords= FXCollections.observableArrayList();
                ObservableList<EmailRecord> existingData = tableView.getItems();
                emailRecords.addAll(existingData);
                while (rs.next())
                {
                    String studentID = rs.getString("StudentID");
                    String studentName = rs.getString("Name");
                    String email = rs.getString("Email");
                    emailRecords.add(new EmailRecord(studentID,studentName,email));
                }
                tableView.setItems(emailRecords);
            }
        }
    }
    public void SendEmail() throws SQLException {
        int selectedCount = count;
        Alert sendEmailAlert = new Alert(Alert.AlertType.CONFIRMATION);
        sendEmailAlert.setTitle("Send Email");
        sendEmailAlert.setHeaderText(null);
        sendEmailAlert.setContentText("Send email to " + selectedCount + " people.");
        ButtonType buttonOK = new ButtonType("OK");
        ButtonType buttonCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        sendEmailAlert.getButtonTypes().setAll(buttonOK, buttonCancel);
        Optional<ButtonType> result = sendEmailAlert.showAndWait();
        if (result.isPresent() && result.get() == buttonOK) {
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Email successfully sent.");
            successAlert.showAndWait();
        } else {
        }
    }
    public void Discard()
    {
        count=0;
        tableView.getItems().clear();
        nameBox.getItems().clear();
        mesTextfield.clear();
    }
    public void Back(ActionEvent event) throws IOException {
        FXMLLoader Back = new FXMLLoader(Main.class.getResource("InstructorPlatform.fxml"));
        root = Back.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        BackGroundScene backGroundScene = new BackGroundScene();
        InstructorPlatform instructorPlatform=Back.getController();
        instructorPlatform.displayInsName(username);
        StackPane stackPane = new StackPane(backGroundScene.getBackgroundView(), root);
        scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.show();
    }
}
