package Controller.ExamStu;

import java.sql.Date;
import java.sql.Time;

public class ExamRecord {
    private String courseID;
    private String courseName;
    private Date examDate;
    private Time examTime;
    private String examRoom;
    public ExamRecord(String courseID, String courseName,Date examDate, Time examTime, String examRoom) {
        this.courseID = courseID;
        this.examDate = examDate;
        this.examTime = examTime;
        this.examRoom = examRoom;
        this.courseName = courseName;
    }
    public String getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public Date getExamDate() {
        return examDate;
    }

    public Time getExamTime() {
        return examTime;
    }

    public String getExamRoom() {
        return examRoom;
    }
}
