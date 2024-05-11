package Controller.GradesStudent;

public class GradesModel {
    private String studentID, courseID, courseName, overallMarkCharacter, semester;
    private int credit;
    private int progressMark;
    private int midtermMark;
    private int finalMark;
    private int overallMark;

    public GradesModel(String studentID, String courseID, String courseName, int credit, String semester, int progressMark, int midtermMark, int finalMark, int overallMark, String overallMarkCharacter) {
        this.studentID = studentID;
        this.courseID = courseID;
        this.courseName = courseName;
        this.credit = credit;
        this.semester = semester;
        this.progressMark = progressMark;
        this.midtermMark = midtermMark;
        this.finalMark = finalMark;
        this.overallMark = overallMark;
        this.overallMarkCharacter = overallMarkCharacter;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getOverallMarkCharacter() {
        return overallMarkCharacter;
    }

    public int getCredit() {
        return credit;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getProgressMark() {
        return progressMark;
    }

    public int getMidtermMark() {
        return midtermMark;
    }

    public int getFinalMark() {
        return finalMark;
    }

    public int getOverallMark() {
        return overallMark;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setOverallMarkCharacter(String overallMarkCharacter) {
        this.overallMarkCharacter = overallMarkCharacter;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public void setProgressMark(Integer progressMark) {
        this.progressMark = progressMark;
    }

    public void setMidtermMark(Integer midtermMark) {
        this.midtermMark = midtermMark;
    }

    public void setFinalMark(Integer finalMark) {
        this.finalMark = finalMark;
    }

    public void setOverallMark(Integer overallMark) {
        this.overallMark = overallMark;
    }
}
