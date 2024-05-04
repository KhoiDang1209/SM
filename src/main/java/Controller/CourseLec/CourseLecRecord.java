package Controller.CourseLec;

import java.sql.Date;
import java.sql.Time;

public class CourseLecRecord {
    private String courseID;
    private String courseName;
    private int credit;
    private String room;
    private Date sdate;
    private Date edate;
    private Time stime;
    private Time etime;
    private String dow;

    public CourseLecRecord(String courseID,String courseName, int credit, String room, Date sdate, Date edate,Time stime, Time etime, String dow) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.credit = credit;
        this.room = room;
        this.sdate = sdate;
        this.edate = edate;
        this.stime = stime;
        this.etime = etime;
        this.dow = dow;
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
    public String getRoom() {
        return room;
    }
    public Date getSdate() {
        return sdate;
    }
    public Date getEdate() {
        return edate;
    }
    public Time getStime() {
        return stime;
    }
    public Time getEtime() {
        return etime;
    }
    public String getDow() {
        return dow;
    }
}
