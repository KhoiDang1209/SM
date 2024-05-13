package Controller.Admin;

import java.sql.Date;
import java.sql.Time;

public class CourseRecord {
    private String courseID;
    private String lecturerID;
    private String courseName;
    private int credit;
    private String room;
    private Time startTime;
    private Time endTime;
    private String DoW;
    public CourseRecord(String courseID, String lecturerID, String courseName, int credit, String room, Time startTime, Time endTime, String DoW) {
        this.courseID = courseID;
        this.lecturerID = lecturerID;
        this.courseName = courseName;
        this.credit = credit;
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
        this.DoW = DoW;
    }

    public String getCourseID() {
        return courseID;
    }

    public String getLecturerID() {
        return lecturerID;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredit() {
        return credit;
    }

    public String getRoom() {
        return room;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public String getDoW() {
        return DoW;
    }
}
