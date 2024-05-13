package Controller.Admin;

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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.sm.Main;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

import static Controller.Login.username;

public class Admin {
    @FXML
    private Label nameLabel;
    public void displayInsName(String id)
    {
        nameLabel.setText("Welcome "+id+" !");
    }
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button confirmButton;
    @FXML
    private Pane newcoursePane;
    @FXML
    private TextField courseIDTf;
    @FXML
    private TextField lecIDTf;
    @FXML
    private TextField creditTf;
    @FXML
    private TextField courseNameTf;
    @FXML
    private TextField roomTf;
    @FXML
    private TextField stimeTf;
    @FXML
    private TextField etimeTf;
    @FXML
    private TextField dowTf;
    @FXML
    private DatePicker sdayTf;
    @FXML
    private DatePicker edayTf;
    @FXML
    private TableView<CourseRecord> courseTable;
    @FXML
    private TableColumn<CourseRecord,String> CourseIDCol;
    @FXML
    private TableColumn<CourseRecord,String> LecturerIDCol;
    @FXML
    private TableColumn<CourseRecord,String> CourseNameCol;
    @FXML
    private TableColumn<CourseRecord,Integer> CreditCol;
    @FXML
    private TableColumn<CourseRecord,String> RoomCol;
    @FXML
    private TableColumn<CourseRecord, Time> StarttimeCol;
    @FXML
    private TableColumn<CourseRecord,Time> EndtimeCol;
    @FXML
    private TableColumn<CourseRecord,String> DoWCol;
    public void initialize() throws SQLException {
        newcoursePane.setVisible(false);
        confirmButton.setVisible(false);
        CourseIDCol.setCellValueFactory(new PropertyValueFactory<CourseRecord,String>("courseID"));
        LecturerIDCol.setCellValueFactory(new PropertyValueFactory<CourseRecord,String>("lecturerID"));
        CourseNameCol.setCellValueFactory(new PropertyValueFactory<CourseRecord,String>("courseName"));
        CreditCol.setCellValueFactory(new PropertyValueFactory<CourseRecord,Integer>("credit"));
        RoomCol.setCellValueFactory(new PropertyValueFactory<CourseRecord,String>("room"));
        StarttimeCol.setCellValueFactory(new PropertyValueFactory<CourseRecord,Time>("startTime"));
        EndtimeCol.setCellValueFactory(new PropertyValueFactory<CourseRecord,Time>("endTime"));
        DoWCol.setCellValueFactory(new PropertyValueFactory<CourseRecord,String>("DoW"));
        Connection cn= DatabaseConnection.getConnection();
        if(cn!=null)
        {
            String queryCourse="Select c.CourseID, c.LecturerID, c.CourseName, c.Credit, c.Room, c.Start_Time,c.End_Time,c.Day_of_Week from Course as c";
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(queryCourse);
            ObservableList<CourseRecord> courseRecords= FXCollections.observableArrayList();
            while (rs.next())
            {
                String couid=rs.getString("CourseID");
                String lecid=rs.getString("LecturerID");
                String coursename=rs.getString("CourseName");
                int credit=rs.getInt("Credit");
                String room=rs.getString("Room");
                Time starttime=rs.getTime("Start_Time");
                Time endtime=rs.getTime("End_Time");
                String dayofweek=rs.getString("Day_of_Week");
                courseRecords.add(new CourseRecord(couid,lecid,coursename,credit,room,starttime,endtime,dayofweek));
            }
            courseTable.setItems(courseRecords);
        }
    }
    public void LogOut(ActionEvent event) throws IOException {
        // Create a confirmation alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to log out?");

        // Add OK and Cancel buttons
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Handle event when user clicks OK button
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    // Load the login page
                    FXMLLoader loader = new FXMLLoader(Main.class.getResource("Login.fxml"));
                    Parent root = loader.load();
                    BackGroundScene backGroundScene = new BackGroundScene();
                    StackPane stackPane = new StackPane(backGroundScene.getBackgroundView(), root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene1=new Scene(stackPane);
                    stage.setScene(scene1);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void addNewCourse()
    {
        newcoursePane.setVisible(true);
        confirmButton.setVisible(true);
    }
    public void confirmNewCourse() throws SQLException {
        String courseID = courseIDTf.getText();
        String lecturerID = lecIDTf.getText();
        String courseName = courseNameTf.getText();
        int credit = Integer.parseInt(creditTf.getText());
        String room = roomTf.getText();
        String startTime = stimeTf.getText();
        LocalTime stime = LocalTime.parse(startTime);
        String endTime = etimeTf.getText();
        LocalTime etime = LocalTime.parse(endTime);
        String dayofweek = dowTf.getText();
        LocalDate sdate = sdayTf.getValue();
        LocalDate edate = edayTf.getValue();

        // Tạo thông báo hiển thị giá trị cần thêm
        String confirmationMessage = "Course ID: " + courseID + "\n" +
                "Lecturer ID: " + lecturerID + "\n" +
                "Course Name: " + courseName + "\n" +
                "Credit: " + credit + "\n" +
                "Room: " + room + "\n" +
                "Start Time: " + stime + "\n" +
                "End Time: " + etime + "\n" +
                "Day of Week: " + dayofweek + "\n" +
                "Start Date: " + sdate + "\n" +
                "End Date: " + edate;

        // Tạo một thông báo xác nhận
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation");
        confirmAlert.setHeaderText("Please review the following course information:");
        confirmAlert.setContentText(confirmationMessage);

        // Thêm nút OK và Cancel
        confirmAlert.getButtonTypes().clear();
        confirmAlert.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Hiển thị thông báo và xử lý sự kiện khi người dùng chọn OK
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Nếu người dùng chọn OK, thực hiện thêm vào cơ sở dữ liệu
                try {
                    Connection conn = DatabaseConnection.getConnection();
                    if (conn != null) {
                        String insertCourseQuery = "INSERT INTO Course (CourseID, LecturerID, CourseName, Credit, Room, Start_Day, End_Day, Start_Time, End_Time, Day_of_Week) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement ps = conn.prepareStatement(insertCourseQuery)) {
                            ps.setString(1, courseID);
                            ps.setString(2, lecturerID);
                            ps.setString(3, courseName);
                            ps.setInt(4, credit);
                            ps.setString(5, room);
                            ps.setDate(6, Date.valueOf(sdate));
                            ps.setDate(7, Date.valueOf(edate));
                            ps.setTime(8, Time.valueOf(stime));
                            ps.setTime(9, Time.valueOf(etime));
                            ps.setString(10, dayofweek);
                            ps.executeUpdate();
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void Lecturer(ActionEvent event) throws IOException {
        FXMLLoader AdminLecLoader = new FXMLLoader(Main.class.getResource("AdminLec.fxml"));
        root = AdminLecLoader.load();
        AdminLec adminLec=AdminLecLoader.getController();
        adminLec.displayInsName(username);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        BackGroundScene backGroundScene = new BackGroundScene();
        StackPane stackPane = new StackPane(backGroundScene.getBackgroundView(), root);
        scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.show();
    }
    public void refreshTable() throws SQLException {
        Connection cn= DatabaseConnection.getConnection();
        if(cn!=null)
        {
            String queryCourse="Select c.CourseID, c.LecturerID, c.CourseName, c.Credit, c.Room, c.Start_Time,c.End_Time,c.Day_of_Week from Course as c";
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(queryCourse);
            ObservableList<CourseRecord> courseRecords= FXCollections.observableArrayList();
            while (rs.next())
            {
                String couid=rs.getString("CourseID");
                String lecid=rs.getString("LecturerID");
                String coursename=rs.getString("CourseName");
                int credit=rs.getInt("Credit");
                String room=rs.getString("Room");
                Time starttime=rs.getTime("Start_Time");
                Time endtime=rs.getTime("End_Time");
                String dayofweek=rs.getString("Day_of_Week");
                courseRecords.add(new CourseRecord(couid,lecid,coursename,credit,room,starttime,endtime,dayofweek));
            }
            courseTable.getItems().clear();
            courseTable.setItems(courseRecords);
        }
    }
}
