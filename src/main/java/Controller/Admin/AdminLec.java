package Controller.Admin;

import Controller.DatabaseConnection;
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
import java.util.AbstractCollection;
import java.util.Date;

import static Controller.Login.username;

public class AdminLec {
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
    private TableView<LecRecord> lecTable;
    @FXML
    private TableColumn<LecRecord,String> lecIDcol;
    @FXML
    private TableColumn<LecRecord,String> lecNamecol;
    @FXML
    private TableColumn<LecRecord,String> lecEmailcol;
    @FXML
    private TableColumn<LecRecord,String> phonecol;
    @FXML
    private TableColumn<LecRecord,String> fieldcol;
    @FXML
    private TableColumn<LecRecord, Date> jdatecol;
    @FXML
    private TextField LecIDTF;
    @FXML
    private TextField LecNameTF;
    @FXML
    private TextField LecEmailTF;
    @FXML
    private TextField FieldTF;
    @FXML
    private TextField PhoneTF;
    @FXML
    private DatePicker jdateTF;
    @FXML
    private Pane newlecPane;
    public void initialize() throws SQLException {
        lecIDcol.setCellValueFactory(new PropertyValueFactory<LecRecord,String>("lecturerID"));
        lecNamecol.setCellValueFactory(new PropertyValueFactory<LecRecord,String>("lecturerName"));
        lecEmailcol.setCellValueFactory(new PropertyValueFactory<LecRecord,String>("lecturerEmail"));
        phonecol.setCellValueFactory(new PropertyValueFactory<LecRecord,String>("lecturerPhone"));
        fieldcol.setCellValueFactory(new PropertyValueFactory<LecRecord,String>("field"));
        jdatecol.setCellValueFactory(new PropertyValueFactory<LecRecord,Date>("joinDate"));
        newlecPane.setVisible(false);
        confirmButton.setVisible(false);
        Connection cn= DatabaseConnection.getConnection();
        if(cn!=null)
        {
            String queryLec="select l.LecturerID, l.LecturerName, l.LecturerEmail, l.LecturerPhone, l.Field, l.Join_Date from Lecturer as l";
            Statement st= cn.createStatement();
            ResultSet resultSet=st.executeQuery(queryLec);
            ObservableList<LecRecord> lecRecords= FXCollections.observableArrayList();
            while (resultSet.next())
            {
                String lecID=resultSet.getString("LecturerID");
                String lecName=resultSet.getString("LecturerName");
                String lecEmail=resultSet.getString("LecturerEmail");
                String lecPhone=resultSet.getString("LecturerPhone");
                String field=resultSet.getString("Field");
                java.sql.Date joinDate=resultSet.getDate("Join_Date");
                lecRecords.add(new LecRecord(lecID,lecName,lecEmail,lecPhone,field,joinDate));
            }
            lecTable.setItems(lecRecords);
        }
    }
    public void addNewLecturer()
    {
        newlecPane.setVisible(true);
        confirmButton.setVisible(true);
    }
    public void confirmNewLec() throws SQLException {
        String lecID = LecIDTF.getText();
        String lecName = LecNameTF.getText();
        String lecEmail = LecEmailTF.getText();
        String lecPhone = PhoneTF.getText();
        String field = FieldTF.getText();
        LocalDate joinDate = jdateTF.getValue();

        // Tạo thông báo hiển thị giá trị cần thêm
        String confirmationMessage = "Lecturer ID: " + lecID + "\n" +
                "Lecturer Name: " + lecName + "\n" +
                "Lecturer Email: " + lecEmail + "\n" +
                "Lecturer Phone: " + lecPhone + "\n" +
                "Field: " + field + "\n" +
                "Join Date: " + joinDate;

        // Tạo một thông báo xác nhận
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation");
        confirmAlert.setHeaderText("Please review the following lecturer information:");
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
                        String insertLecQuery = "INSERT INTO Lecturer (LecturerID, LecturerName, LecturerEmail, LecturerPhone, Field, Join_Date) VALUES (?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement ps = conn.prepareStatement(insertLecQuery)) {
                            ps.setString(1, lecID);
                            ps.setString(2, lecName);
                            ps.setString(3, lecEmail);
                            ps.setString(4, lecPhone);
                            ps.setString(5, field);
                            ps.setDate(6, java.sql.Date.valueOf(joinDate));
                            ps.executeUpdate();
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try{
                    Connection connection=DatabaseConnection.getConnection();
                    if(connection!=null)
                    {
                        int countUser=0;
                        String selectquery="Select Count(UserID) as total from [User]";
                        Statement statement=connection.createStatement();
                        ResultSet rs=statement.executeQuery(selectquery);
                        while (rs.next())
                        {
                            countUser=rs.getInt("total");
                        }
                        String insertquery="Insert into [User] (UserID, UserName, UserPassword, Role) VALUES (?,?,1,?)";
                        try (PreparedStatement ps = connection.prepareStatement(insertquery)) {
                            ps.setInt(1, countUser);
                            ps.setString(2, lecID);
                            ps.setString(3, "Lecturer");
                            ps.executeUpdate();
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        refreshTable();
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
    public void refreshTable() throws SQLException {
        ObservableList<LecRecord> lecRecords = FXCollections.observableArrayList();
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            String queryLec = "SELECT l.LecturerID, l.LecturerName, l.LecturerEmail, l.LecturerPhone, l.Field, l.Join_Date FROM Lecturer AS l";
            try (Statement st = conn.createStatement();
                 ResultSet resultSet = st.executeQuery(queryLec)) {
                while (resultSet.next()) {
                    String lecID = resultSet.getString("LecturerID");
                    String lecName = resultSet.getString("LecturerName");
                    String lecEmail = resultSet.getString("LecturerEmail");
                    String lecPhone = resultSet.getString("LecturerPhone");
                    String field = resultSet.getString("Field");
                    java.sql.Date joinDate = resultSet.getDate("Join_Date");
                    lecRecords.add(new LecRecord(lecID, lecName, lecEmail, lecPhone, field, joinDate));
                }
            }
        }
        lecTable.getItems().clear(); // Xóa tất cả các mục hiện có trong TableView
        lecTable.setItems(lecRecords); // Thêm lại các mục mới vào TableView
    }
    public void Course(ActionEvent event) throws IOException {
        FXMLLoader AdminLoader = new FXMLLoader(Main.class.getResource("Admin.fxml"));
        root = AdminLoader.load();
        Admin admin=AdminLoader.getController();
        admin.displayInsName(username);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        BackGroundScene backGroundScene = new BackGroundScene();
        StackPane stackPane = new StackPane(backGroundScene.getBackgroundView(), root);
        scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.show();
    }
}
