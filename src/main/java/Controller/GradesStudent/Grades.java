package Controller.GradesStudent;

import Controller.DatabaseConnection;
import Controller.Login;
import Controller.StudentPlatform;
import image.BackGroundScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.sm.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Controller.Login.username;

public class Grades implements Initializable {
    @FXML
    Label nameLabel;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TableView<GradesModel> gradesTableView;
    @FXML
    private TableColumn<GradesModel, String> studentIDTableColumn;
    @FXML
    private TableColumn<GradesModel, String> courseIDTableColumn;
    @FXML
    private TableColumn<GradesModel, String> coursenameTableColumn;
    @FXML
    private TableColumn<GradesModel, Integer> creditTableColumn;
    @FXML
    private TableColumn<GradesModel, Integer> semesterTableColumn;
    @FXML
    private TableColumn<GradesModel, Integer> progressTableColumn;
    @FXML
    private TableColumn<GradesModel, Integer> midtermTableColumn;
    @FXML
    private TableColumn<GradesModel, Integer> finalTableColumn;
    @FXML
    private TableColumn<GradesModel, Integer> overallTableColumn;
    @FXML
    private TableColumn<GradesModel, String> overallCharacterTableColumn;
    @FXML
    private TextField semesterTextField;
    @FXML
    private Label classLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Label levelLabel;
    @FXML
    private Label majorLabel;
    @FXML
    private Label studentIDLabel;
    @FXML
    private Label studentNameLabel;
    @FXML
    private Label creditLabel;
    @FXML
    private Label GPALabel;
    String semester;
    int[] credits= new int[40];
    int[] overalls=new int[40];
    int count=-1;
    ObservableList<GradesModel> gradesModelObservableList = FXCollections.observableArrayList();

    @FXML
    public void View(ActionEvent event) {
        semester = semesterTextField.getText();
        loadDataFromDatabase();
    }

    public void setStudentIDLabel(String studentID) {
        studentIDLabel.setText(studentID);
    }
    public void setStudentNameLabel(String studentName) {
        studentNameLabel.setText(studentName);
    }
    public void setStudentGenderLabel(String studentGender) {
        genderLabel.setText(studentGender);
    }
    public void setStudentClassLabel(String studentClass) {
        classLabel.setText(studentClass);
    }
    public void setStudentMajorLabel(String studentMajor) {
        majorLabel.setText(studentMajor);
    }
    public void setStudentLevelLabel(String studentLevel) {
        levelLabel.setText(studentLevel);
    }

    @Override
    public void initialize(URL url, ResourceBundle resource) {
        DatabaseConnection connectionNow = new DatabaseConnection();
        Connection connectDB = null;
        Statement statement = null;
        ResultSet queryOutput = null;

        connectDB = DatabaseConnection.getConnection();

        if (connectDB != null) {
            String gradesViewQuery = "SELECT e.StudentID, e.CourseID, c.CourseName, c.Credit, e.Semester, e.Progress, e.Midterm, e.Final, e.Overall, e.OverallCharacter, s.Name, s.Gender, s.Class, s.Major, s.Level FROM Enroll as e, Course as c, Student as s WHERE e.CourseID = c.CourseID AND e.StudentID = '" + Login.username + "' AND e.StudentID = s.StudentID";

            try {
                statement = connectDB.createStatement();
                queryOutput = statement.executeQuery(gradesViewQuery);

                while (queryOutput.next()) {
                    count++;
                    String queryStudentID = queryOutput.getString("StudentID");
                    String queryCourseID = queryOutput.getString("CourseID");
                    String queryCourseName = queryOutput.getString("CourseName");
                    int queryCredit = queryOutput.getInt("Credit");
                    credits[count]= queryOutput.getInt("Credit");
                    String querySemester = queryOutput.getString("Semester");
                    int queryProgress = queryOutput.getInt("Progress");
                    int queryMidterm = queryOutput.getInt("Midterm");
                    int queryFinalMark = queryOutput.getInt("Final");
                    int queryOverall = queryOutput.getInt("Overall");
                    overalls[count]=queryOutput.getInt("Overall");
                    String queryOverallCharacter = queryOutput.getString("OverallCharacter");
                    String queryStudentName = queryOutput.getString("Name");
                    String queryStudentGender = queryOutput.getString("Gender");
                    String queryStudentClass = queryOutput.getString("Class");
                    String queryStudentMajor = queryOutput.getString("Major");
                    String queryStudentLevel = queryOutput.getString("Level");

                    setStudentIDLabel(queryStudentID);
                    setStudentNameLabel(queryStudentName);
                    setStudentGenderLabel(queryStudentGender);
                    setStudentClassLabel(queryStudentClass);
                    setStudentMajorLabel(queryStudentMajor);
                    setStudentLevelLabel(queryStudentLevel);

                    gradesModelObservableList.add(new GradesModel(queryStudentID, queryCourseID, queryCourseName, queryCredit, querySemester, queryProgress, queryMidterm, queryFinalMark, queryOverall, queryOverallCharacter));
                }

                studentIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
                courseIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("courseID"));
                coursenameTableColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
                creditTableColumn.setCellValueFactory(new PropertyValueFactory<>("credit"));
                semesterTableColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));
                progressTableColumn.setCellValueFactory(new PropertyValueFactory<>("progressMark"));
                midtermTableColumn.setCellValueFactory(new PropertyValueFactory<>("midtermMark"));
                finalTableColumn.setCellValueFactory(new PropertyValueFactory<>("finalMark"));
                overallTableColumn.setCellValueFactory(new PropertyValueFactory<>("overallMark"));
                overallCharacterTableColumn.setCellValueFactory(new PropertyValueFactory<>("overallMarkCharacter"));

                gradesTableView.setItems(gradesModelObservableList);

            } catch (SQLException e) {
                Logger.getLogger(Grades.class.getName()).log(Level.SEVERE, null, e);
            } finally {
                try {
                    if (queryOutput != null)
                        queryOutput.close();
                    if (statement != null)
                        statement.close();
                    if (connectDB != null)
                        connectDB.close();
                } catch (SQLException e) {
                    Logger.getLogger(Grades.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        } else {
            Logger.getLogger(Grades.class.getName()).log(Level.SEVERE, "Database connection is null");
        }
        int accCredits=0;
        double gpa=0;
        for(int i=0;i<count;i++)
        {
            accCredits+=credits[i];
            gpa+=credits[i]*overalls[i];
        }
        gpa=gpa/accCredits;
        displayCredits(accCredits);
        displayGPA(gpa);
    }
    public void displayCredits(int n)
    {
        creditLabel.setText("Accumulated credits: "+ n);
    }
    public void displayGPA(double n)
    {
        String formattedGPA = String.format("%.2f", n);
        GPALabel.setText("Overall GPA: " + formattedGPA);
    }
    private void loadDataFromDatabase() {
        gradesModelObservableList.clear(); // Clear the existing data

        DatabaseConnection connectionNow = new DatabaseConnection();
        Connection connectDB;
        Statement statement = null;
        ResultSet queryOutput = null;

        connectDB = DatabaseConnection.getConnection();

        if (connectDB != null) {
            String gradesViewQuery;
            if (semester == null || semester.isEmpty()) {
                gradesViewQuery = "SELECT e.StudentID, e.CourseID, c.CourseName, c.Credit, e.Semester, e.Progress, e.Midterm, e.Final, e.Overall, e.OverallCharacter, s.Name, s.Gender,s.Class, s.Major, s.Level FROM Enroll as e, Course as c, Student as s WHERE e.CourseID = c.CourseID AND e.StudentID = '" + Login.username + "' AND e.StudentID = s.StudentID";
            } else {
                gradesViewQuery = "SELECT e.StudentID, e.CourseID, c.CourseName, c.Credit, e.Semester, e.Progress, e.Midterm, e.Final, e.Overall, e.OverallCharacter, s.Name, s.Gender, s.Class, s.Major, s.Level FROM Enroll as e, Course as c, Student as s WHERE e.CourseID = c.CourseID AND e.StudentID = '" + Login.username + "' AND e.StudentID = s.StudentID AND e.Semester = '" + semester + "'";
            }

            try {
                statement = connectDB.createStatement();
                queryOutput = statement.executeQuery(gradesViewQuery);

                while (queryOutput.next()) {
                    String queryStudentID = queryOutput.getString("StudentID");
                    String queryCourseID = queryOutput.getString("CourseID");
                    String queryCourseName = queryOutput.getString("CourseName");
                    int queryCredit = queryOutput.getInt("Credit");
                    String querySemester = queryOutput.getString("Semester");
                    int queryProgress = queryOutput.getInt("Progress");
                    int queryMidterm = queryOutput.getInt("Midterm");
                    int queryFinalMark = queryOutput.getInt("Final");
                    int queryOverall = queryOutput.getInt("Overall");
                    String queryOverallCharacter = queryOutput.getString("OverallCharacter");

                    gradesModelObservableList.add(new GradesModel( queryStudentID, queryCourseID, queryCourseName, queryCredit, querySemester, queryProgress, queryMidterm, queryFinalMark, queryOverall, queryOverallCharacter));
                }

                studentIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
                courseIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("courseID"));
                coursenameTableColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
                creditTableColumn.setCellValueFactory(new PropertyValueFactory<>("credit"));
                semesterTableColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));
                progressTableColumn.setCellValueFactory(new PropertyValueFactory<>("progressMark"));
                midtermTableColumn.setCellValueFactory(new PropertyValueFactory<>("midtermMark"));
                finalTableColumn.setCellValueFactory(new PropertyValueFactory<>("finalMark"));
                overallTableColumn.setCellValueFactory(new PropertyValueFactory<>("overallMark"));
                overallCharacterTableColumn.setCellValueFactory(new PropertyValueFactory<>("overallMarkCharacter"));

                gradesTableView.setItems(gradesModelObservableList);

            } catch (SQLException e) {
                Logger.getLogger(Grades.class.getName()).log(Level.SEVERE, null, e);
            } finally {
                try {
                    if (queryOutput != null)
                        queryOutput.close();
                    if (statement != null)
                        statement.close();
                    if (connectDB != null)
                        connectDB.close();
                } catch (SQLException e) {
                    Logger.getLogger(Grades.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        } else {
            Logger.getLogger(Grades.class.getName()).log(Level.SEVERE, "Database connection is null");
        }
    }

    public void displayStuName(String id) {
        nameLabel.setText("Welcome " + id + " !");
    }

    public void Back(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("StudentPlatform.fxml"));
            root = loader.load();
            StudentPlatform studentController = loader.getController();
            studentController.displayStuName(username);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            BackGroundScene backGroundScene = new BackGroundScene();
            StackPane stackPane = new StackPane(backGroundScene.getBackgroundView(), root);
            scene = new Scene(stackPane);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Logger.getLogger(Grades.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
