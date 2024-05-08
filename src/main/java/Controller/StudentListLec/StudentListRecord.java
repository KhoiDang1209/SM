package Controller.StudentListLec;

public class StudentListRecord {
    private String studentID;
    private String studentName;
    private int progress;
    private int midterm;
    private int finalG;
    private int overall;
    private String overallC;
    public StudentListRecord(String studentID, String studentName, int progress,int midterm, int finalG, int overall, String overallC) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.progress = progress;
        this.midterm = midterm;
        this.finalG = finalG;
        this.overall = overall;
        this.overallC = overallC;
    }
    public String getStudentID() {
        return studentID;
    }
    public String getStudentName() {
        return studentName;
    }
    public int getProgress() {
        return progress;
    }
    public int getMidterm() {
        return midterm;
    }
    public int getFinalG() {
        return finalG;
    }
    public int getOverall() {
        return overall;
    }
    public String getOverallC() {
        return overallC;
    }
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public void setProgress(int progress) {
        this.progress = progress;
    }
    public void setMidterm(int midterm) {
        this.midterm = midterm;
    }
    public void setFinalG(int finalG) {
        this.finalG = finalG;
    }
    public void setOverall(int overall) {
        this.overall = overall;
    }
    public void setOverallC(String overallC) {
        this.overallC = overallC;
    }
}
