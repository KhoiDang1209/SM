package Controller.Tuition;

public class NewCourseRecord {
    private String courseID;
    private String courseName;
    private int credit;
    private String Semester;
    public NewCourseRecord(String courseID, String courseName, int credit, String semester) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.credit = credit;
        this.Semester = semester;
    }
    public String getCourseID() {
        return courseID;
    }
    public String getCourseName() {
        return courseName;
    }
    public int getCredit() {
        return credit;
    }
    public String getSemester() {
        return Semester;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }
}
