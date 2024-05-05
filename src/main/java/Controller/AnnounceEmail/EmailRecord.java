package Controller.AnnounceEmail;

public class EmailRecord {
    private String studentID;
    private String studentName;
    private String email;
    public EmailRecord(String studentID, String studentName, String email) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.email = email;
    }
    public String getStudentID() {
        return studentID;
    }
    public String getStudentName() {
        return studentName;
    }
    public String getEmail() {
        return email;
    }
}
