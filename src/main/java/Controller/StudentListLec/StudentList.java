package Controller.StudentListLec;

import Controller.DatabaseConnection;
import Controller.InstructorPlatform;
import Controller.PersonalEmail;
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
import javafx.util.Callback;
import javafx.event.EventHandler;
import javafx.beans.binding.Bindings;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Controller.Login.username;

public class StudentList {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private ComboBox<String> courseBox;
    @FXML
    private TableView<StudentListRecord> studentView;
    @FXML
    private TableColumn<StudentListRecord, String> stuidCol;
    @FXML
    private TableColumn<StudentListRecord, String> stunameCol;
    @FXML
    private TableColumn<StudentListRecord, String> genderCol;
    @FXML
    private TableColumn<StudentListRecord, String> majorCol;
    @FXML
    private TableColumn<StudentListRecord, String> emailCol;
    @FXML
    private TableColumn<StudentListRecord, String> levelCol;
    public void initialize() throws SQLException {
        stuidCol.setCellValueFactory(new PropertyValueFactory<StudentListRecord, String>("studentID"));
        stunameCol.setCellValueFactory(new PropertyValueFactory<StudentListRecord,String>("studentName"));
        genderCol.setCellValueFactory(new PropertyValueFactory<StudentListRecord,String>("gender"));
        majorCol.setCellValueFactory(new PropertyValueFactory<StudentListRecord,String>("major"));
        emailCol.setCellValueFactory(new PropertyValueFactory<StudentListRecord,String>("email"));
        levelCol.setCellValueFactory(new PropertyValueFactory<StudentListRecord,String>("level"));
        studentView.setVisible(false);
        Connection cn = DatabaseConnection.getConnection();
        if(cn!=null)
        {
            String querycourse="Select CourseID from Course where LecturerID=?";
            try(PreparedStatement ps=cn.prepareStatement(querycourse)) {
                ps.setString(1,username);
                try(ResultSet rs=ps.executeQuery()) {
                    while (rs.next()) {
                        String courseID = rs.getString("CourseID");
                        courseBox.getItems().add(courseID);
                    }
                }
            }
        }

        studentView.setRowFactory(
                new Callback<TableView<StudentListRecord>, TableRow<StudentListRecord>>() {
                    @Override
                    public TableRow<StudentListRecord> call(TableView<StudentListRecord> tableView) {
                        final TableRow<StudentListRecord> row = new TableRow<>();
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem emailItem = new MenuItem("Email");
                        emailItem.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                emailItem.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        try {
                                            loadPersonalEmail(event);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                            }
                        });

                        rowMenu.getItems().addAll(emailItem);

                        // only display context menu for non-null items:
                        row.contextMenuProperty().bind(
                                Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                        .then(rowMenu)
                                        .otherwise((ContextMenu)null));
                        return row;
                    }
                });

    }

    private void loadPersonalEmail(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Email.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void View() throws SQLException {
        Connection cn = DatabaseConnection.getConnection();
        String selectedcourse=courseBox.getValue();
        String queryStudent="Select s.StudentID, s.Name, s.Gender, s.Major, s.Email, s.Level from Student as s, Enroll as er where er.CourseID=? and er.StudentID=s.StudentID";
        try(PreparedStatement ps=cn.prepareStatement(queryStudent))
        {
            ps.setString(1,selectedcourse);
            try(ResultSet rs=ps.executeQuery()) {
                ObservableList<StudentListRecord> studentListRecords= FXCollections.observableArrayList();
                while (rs.next()) {
                    String studentID = rs.getString("StudentID");
                    String studentName = rs.getString("Name");
                    String gender = rs.getString("Gender");
                    String major = rs.getString("Major");
                    String email = rs.getString("Email");
                    String level = rs.getString("Level");
                    studentListRecords.add(new StudentListRecord(studentID,studentName,gender,major,email,level));
                }
                studentView.setItems(studentListRecords);
            }
        }
        studentView.setVisible(true);
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