package Controller.TimeTable;

import java.sql.Time;

public class TimeTableRecord {
    private String courseID;
    private String courseName;
    private Time StartTime;
    private Time EndTime;
    private String Room;
    private String Day_of_Week;

    public TimeTableRecord(String courseID, String courseName, Time StartTime, Time EndTime, String Room, String Day_of_Week) {
        this.courseID = courseID;
        this.StartTime = StartTime;
        this.EndTime = EndTime;
        this.Room = Room;
        this.courseName = courseName;
        this.Day_of_Week = Day_of_Week;
    }
    public String getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public Time getStartTime() {
        return StartTime;
    }

    public Time getEndTime() {
        return EndTime;
    }

    public String getRoom() {
        return Room;
    }
    public String getDay_of_Week() {
        return Day_of_Week;
    }
}
