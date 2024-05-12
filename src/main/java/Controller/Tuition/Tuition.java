package Controller.Tuition;

import Controller.DatabaseConnection;
import Controller.StudentPlatform;
import image.BackGroundScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.sm.Main;
import java.io.IOException;
import java.sql.*;

import static Controller.Login.username;

public class Tuition {
    private Stage stage;
    private Scene scene;
    private Parent root;
    String stuid=null;
    String name=null;
    Date date=null;
    String tuiid=null;
    int amount;
    Date payDate=null;
    String classInfo=null;
    @FXML
    private TableView<TuitionRecord> tuitionTable;
    @FXML
    private TableColumn <TuitionRecord,String> StuIDColumn;
    @FXML
    private TableColumn <TuitionRecord,String> TuiIDColumn;
    @FXML
    private TableColumn <TuitionRecord,Integer> AmountColumn;
    @FXML
    private TableColumn <TuitionRecord,Date> payDateColumn;
    @FXML
    private CheckBox ShowHistory;
    @FXML
    private Pane tuitionPane;
    @FXML
    private Label stuidInfo;
    @FXML
    private Label nameInfo;
    @FXML
    private Label DoBInfo;
    @FXML
    private Label ClassInfo;



    public void setStuidInfo(String stuid) {
        stuidInfo.setText("StudentID: "+stuid);
    }
    public void setNameInfo(String name) {
        nameInfo.setText("Student Fullname: "+name);
    }
    public void setDoBInfo(Date dob) {
        DoBInfo.setText("Date of birth: "+dob);
    }
    public void setClassInfo(String classInfo) {
        ClassInfo.setText("Class: "+classInfo);
    }
    public void Back(ActionEvent event) throws IOException {
        FXMLLoader Back = new FXMLLoader(Main.class.getResource("StudentPlatform.fxml"));
        root = Back.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        BackGroundScene backGroundScene = new BackGroundScene();
        StackPane stackPane = new StackPane(backGroundScene.getBackgroundView(), root);
        StudentPlatform studentController = Back.getController();
        studentController.displayStuName(username);
        scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.show();
    }
    public void initialize() throws SQLException {
        // Ẩn pane khi controller được khởi tạo
        StuIDColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        TuiIDColumn.setCellValueFactory(new PropertyValueFactory<>("tuitionID"));
        AmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        payDateColumn.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        tuitionPane.setVisible(false);
    }
    public void ShowHistoryAction() throws SQLException
    {

        if(ShowHistory.isSelected())
        {
            Connection cn = DatabaseConnection.getConnection();
            if (cn != null) {
                String queryTuition = "SELECT s.StudentID, s.Name, s.DateOfBirth, s.Class, t.TuitionID, t.Amount, t.PaymentDate \n" +
                        "FROM Student AS s\n" +
                        "JOIN Tuition AS t ON s.StudentID = t.StudentID\n" +
                        "WHERE s.StudentID = ?;\n";
                try (PreparedStatement pstmt = cn.prepareStatement(queryTuition)) {
                    pstmt.setString(1, username);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        ObservableList<TuitionRecord> tuitionRecords = FXCollections.observableArrayList();
                        if (rs.next()) {
                            stuid = rs.getString("StudentID");
                            name = rs.getString("Name");
                            date = rs.getDate("DateOfBirth");
                            classInfo = rs.getString("Class");
                            tuiid=rs.getString("TuitionID");
                            amount = rs.getInt("Amount");
                            payDate = rs.getDate("PaymentDate");
                            setStuidInfo(stuid);
                            setNameInfo(name);
                            setDoBInfo(date);
                            setClassInfo(classInfo);
                            tuitionRecords.add(new TuitionRecord(stuid, tuiid, amount, payDate));
                            }
                        tuitionTable.setItems(tuitionRecords);
                        tuitionPane.setVisible(true);
                        }
                    }
                }

        }
        else tuitionPane.setVisible(false);
    }
}
