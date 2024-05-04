package Controller.StudentListLec;

public class StudentListRecord {
    private String studentID;
    private String studentName;
    private String gender;
    private String major;
    private String email;
    private String level;
    public StudentListRecord(String studentID, String studentName, String gender, String major, String email, String level) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.gender = gender;
        this.major = major;
        this.email = email;
        this.level = level;
    }
    public String getStudentID() {
        return studentID;
    }
    public String getStudentName() {
        return studentName;
    }
    public String getGender() {
        return gender;
    }
    public String getMajor() {
        return major;
    }
    public String getEmail() {
        return email;
    }
    public String getLevel() {
        return level;
    }
}
