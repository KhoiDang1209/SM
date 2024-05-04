package Controller.Register;

public class RegisterRec {
    private String CourseID, LecturerID, CourseName;
    private int Credit;
    private int Semester;

    public RegisterRec(String LecturerID, String CourseID, String CourseName, int Credit, int Semester){
        this.CourseID = CourseID;
        this.Credit = Credit;
        this.LecturerID = LecturerID;
        this.Semester = Semester;
        this.CourseName = CourseName;

    }


    public String getLecturerID() {return LecturerID;}

    public void setLecturerID(String LecturerID) {
        this.LecturerID = LecturerID;
    }

    public String getCourseID() {return CourseID;}

    public void setCourseID(String courseID) {
        this.CourseID = CourseID;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        this.CourseName = CourseName;
    }

    public int getCredit() {
        return Credit;
    }

    public void setCredit(Integer Credit) {
        this.Credit = Credit;
    }

    public int getSemester() {
        return Semester;
    }

    public void setSemester(int Semester) {
        this.Semester = Semester;
    }



}
