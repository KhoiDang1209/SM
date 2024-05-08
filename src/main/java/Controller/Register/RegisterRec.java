package Controller.Register;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import java.sql.Time;
import java.util.Date;

public class RegisterRec {
    private String CourseID, CourseName, LecturerName;
    private int Credit;
    private String Room;
    private Time Start;
    private Time End;
    private String Day;

    private CheckBox Action;

    public RegisterRec(String CourseID, String CourseName, int Credit, String Room, String LecturerName, String Day, Time Start, Time End, CheckBox Action) {
        this.CourseID = CourseID;
        this.CourseName = CourseName;
        this.Credit = Credit;
        this.Room = Room;
        this.LecturerName = LecturerName;
        this.Start = Start;
        this.End = End;
        this.Day = Day;
        this.Action = new CheckBox();

    }


    public CheckBox getAction() {
        return Action;
    }

    public Time getStart() {
        return Start;
    }

    public Time getEnd() {
        return End;
    }

    public String getDay() {
        return Day;
    }

    public String getLecturerName() {
        return LecturerName;
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


    public void setAction(CheckBox action) {
        Action = action;
    }

    public void setStart(Time start) {
        Start = start;
    }
    //
    public void setEnd(Time end) {
        End = end;
    }

    ////
    public void setDay(String day) {
        Day = day;
    }

    public void setLecturerName(String lecturerName) {
        LecturerName = lecturerName;
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

