package Controller.StudentListLec;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.sm.Main;
import javafx.util.converter.IntegerStringConverter;
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
    private TableColumn<StudentListRecord, Integer> progress;
    @FXML
    private TableColumn<StudentListRecord, Integer> midterm;
    @FXML
    private TableColumn<StudentListRecord, Integer> finalG;
    @FXML
    private TableColumn<StudentListRecord, Integer> overallG;
    @FXML
    private TableColumn<StudentListRecord, String> overallC;
    public void initialize() throws SQLException {
        stuidCol.setCellValueFactory(new PropertyValueFactory<StudentListRecord, String>("studentID"));
        stunameCol.setCellValueFactory(new PropertyValueFactory<StudentListRecord,String>("studentName"));
        progress.setCellValueFactory(new PropertyValueFactory<StudentListRecord, Integer>("progress"));
        midterm.setCellValueFactory(new PropertyValueFactory<StudentListRecord, Integer>("midterm"));
        finalG.setCellValueFactory(new PropertyValueFactory<StudentListRecord, Integer>("finalG"));
        overallG.setCellValueFactory(new PropertyValueFactory<StudentListRecord, Integer>("overall"));
        overallC.setCellValueFactory(new PropertyValueFactory<StudentListRecord,String>("overallC"));
        studentView.setEditable(true);
        progress.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        midterm.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        finalG.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        setupProgressEditing();
        setupMidtermEditing();
        setupFinalGEditing();
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
    }
    public void View() throws SQLException {
        Connection cn = DatabaseConnection.getConnection();
        String selectedcourse=courseBox.getValue();
        String queryStudent="Select s.StudentID, s.Name, er.Progress, er.Midterm, er.Final, er.Overall, er.OverallCharacter from Student as s, Enroll as er where er.CourseID=? and er.StudentID=s.StudentID and er.Semester='II-2023'";
        try(PreparedStatement ps=cn.prepareStatement(queryStudent))
        {
            ps.setString(1,selectedcourse);
            try(ResultSet rs=ps.executeQuery()) {
                ObservableList<StudentListRecord> studentListRecords= FXCollections.observableArrayList();
                while (rs.next()) {
                    String studentID = rs.getString("StudentID");
                    String studentName = rs.getString("Name");
                    int progress=rs.getInt("Progress");
                    int midterm=rs.getInt("Midterm");
                    int finalG=rs.getInt("Final");
                    int overall=rs.getInt("Overall");
                    String overallC=rs.getString("OverallCharacter");
                    studentListRecords.add(new StudentListRecord(studentID,studentName,progress,midterm,finalG,overall,overallC));
                }
                studentView.setItems(studentListRecords);
            }
        }
        progress.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        midterm.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        finalG.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
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
    private void updateDatabase(String studentID,int progress, int midterm, int finalG, int overall, String overallC) throws SQLException {
        String selectedCourse=courseBox.getValue();
        String queryUpdate="Update Enroll Set Progress=?, Midterm=?, Final=?, Overall=?, OverallCharacter=? Where StudentID=? and CourseID=?";
        Connection cn = DatabaseConnection.getConnection();
        try(PreparedStatement ps=cn.prepareStatement(queryUpdate))
        {
            ps.setInt(1,progress);
            ps.setInt(2,midterm);
            ps.setInt(3,finalG);
            ps.setInt(4, overall);
            ps.setString(5,overallC);
            ps.setString(6,studentID);
            ps.setString(7,selectedCourse);
            ps.executeUpdate();
        }
    }
    private int calculateOverall(int progress, int midterm, int finalG) {
        double overall=progress*0.3+midterm*0.3+finalG*0.4;
        int result=(int) Math.ceil(overall);
        return result;
    }

    // Hàm tính toán overall character dựa trên overall
    private String calculateOverallCharacter(int overall) {
        if(overall<30)
            return "F";
        else if(overall<40)
            return "D";
        else if(overall<50)
            return "D+";
        else if(overall<60)
            return "C";
        else if (overall<70)
            return "B";
        else if(overall<80)
            return "B+";
        else if(overall<90)
            return "A";
        else return "A+";
    }
    @FXML
    private void setupProgressEditing() {
        progress.setOnEditCommit(event -> {
            StudentListRecord record = event.getRowValue();
            record.setProgress(event.getNewValue());
            handleEditCommit(record);
        });
    }

    // Phương thức xử lý sự kiện chỉnh sửa cho cột Midterm
    @FXML
    private void setupMidtermEditing() {
        midterm.setOnEditCommit(event -> {
            StudentListRecord record = event.getRowValue();
            record.setMidterm(event.getNewValue());
            handleEditCommit(record);
        });
    }

    // Phương thức xử lý sự kiện chỉnh sửa cho cột FinalG
    @FXML
    private void setupFinalGEditing() {
        finalG.setOnEditCommit(event -> {
            StudentListRecord record = event.getRowValue();
            record.setFinalG(event.getNewValue());
            handleEditCommit(record);
        });
    }

    // Phương thức xử lý commit chỉnh sửa chung
    private void handleEditCommit(StudentListRecord record) {
        // Tính toán lại giá trị cho overallG và overallC
        int newOverall = calculateOverall(record.getProgress(), record.getMidterm(), record.getFinalG());
        String newOverallC = calculateOverallCharacter(newOverall);
        record.setOverall(newOverall);
        record.setOverallC(newOverallC);
        // Cập nhật giá trị mới vào TableView
        studentView.refresh();
        // Cập nhật giá trị mới vào cơ sở dữ liệu
        try {
            updateDatabase(record.getStudentID(), record.getProgress(), record.getMidterm(), record.getFinalG(), newOverall, newOverallC);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}