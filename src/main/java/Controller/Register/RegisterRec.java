package Controller.Register;

public class RegisterRec {
    private String CourseID, CourseName;
    private int Credit;
    private String Room;

    public RegisterRec(String CourseID, String CourseName, int Credit, String Room) {
        this.CourseID = CourseID;
        this.CourseName = CourseName;
        this.Credit = Credit;
        this.Room = Room;
    }

    public String getCourseID() {
        return CourseID;
    }

    public String getCourseName() {
        return CourseName;
    }

    public int getCredit() {
        return Credit;
    }

    public String getRoom() {
        return Room;
    }

    public void setCourseID(String courseID) {
        CourseID = courseID;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public void setCredit(int credit) {
        Credit = credit;
    }

    public void setRoom(String room) {
        Room = room;
    }
}

