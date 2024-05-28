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
    String semester=null;
    @FXML
    private TableView<NewCourseRecord> CourseTable;
    @FXML
    private TableColumn<NewCourseRecord,String> courseID;
    @FXML
    private TableColumn<NewCourseRecord,String> courseName;
    @FXML
    private TableColumn<NewCourseRecord,Integer> credit;
    @FXML
    private TableColumn<NewCourseRecord,String> newSemester;
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
    private TableColumn <TuitionRecord,String> Semester;
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
    @FXML
    private Label announceLabel;
    @FXML
    private Label announceLabel1;
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
        StuIDColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        TuiIDColumn.setCellValueFactory(new PropertyValueFactory<>("tuitionID"));
        AmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        payDateColumn.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        Semester.setCellValueFactory(new PropertyValueFactory<>("semester"));
        courseID.setCellValueFactory(new PropertyValueFactory<>("courseID"));
        courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        credit.setCellValueFactory(new PropertyValueFactory<>("credit"));
        newSemester.setCellValueFactory(new PropertyValueFactory<>("Semester"));
        tuitionPane.setVisible(false);
        Connection cn=DatabaseConnection.getConnection();
        if(cn!=null)
        {
           String query="SELECT Sum(c.Credit) as totalCredit from Course as c join Enroll as er on c.CourseID=er.CourseID and er.Semester='I-2024' and er.StudentID=?";
           try(PreparedStatement pst=cn.prepareStatement(query)) {
               pst.setString(1, username);
               ResultSet rs = pst.executeQuery();
               while (rs.next()) {
                   int credit = rs.getInt("totalCredit");
                   if (credit == 0) {
                       announceLabel.setText("There are no more receipts that need to be paid during the semester.");
                       CourseTable.setVisible(false);
                       announceLabel1.setVisible(false);
                   } else {
                       String query2="Select c.CourseID, c.CourseName, c.Credit, e.Semester from Course as c join Enroll as e on c.CourseID=e.CourseID and e.Semester='I-2024' and e.StudentID=?";
                       try(PreparedStatement preparedStatement=cn.prepareStatement(query2))
                       {
                           preparedStatement.setString(1,username);
                           ResultSet rs1=preparedStatement.executeQuery();
                           ObservableList<NewCourseRecord> newCourseRecords=FXCollections.observableArrayList();
                           while (rs1.next())
                           {
                               String courseID=rs1.getString("CourseID");
                               String courseName=rs1.getString("CourseName");
                               int credit1=rs1.getInt("Credit");
                               String semester=rs1.getString("Semester");
                               newCourseRecords.add(new NewCourseRecord(courseID,courseName,credit1,semester));
                           }
                           CourseTable.setItems(newCourseRecords);
                       }
                       announceLabel1.setText("You have registered " + credit + " credits. Tuition fee for I-2024 will be: " + 1409567 * credit + " vnd");
                       announceLabel.setVisible(false);
                   }
               }
           }
        }
    }
    public void ShowHistoryAction() throws SQLException
    {

        if(ShowHistory.isSelected())
        {
            Connection cn = DatabaseConnection.getConnection();
            if (cn != null) {
                String queryTuition = "SELECT s.StudentID, s.Name, s.DateOfBirth, s.Class, t.TuitionID, t.Amount, t.PaymentDate, t.Semester \n" +
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
                            semester=rs.getString("Semester");
                            setStuidInfo(stuid);
                            setNameInfo(name);
                            setDoBInfo(date);
                            setClassInfo(classInfo);
                            tuitionRecords.add(new TuitionRecord(stuid, tuiid, amount, payDate,semester));
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
