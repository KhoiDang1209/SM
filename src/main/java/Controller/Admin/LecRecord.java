package Controller.Admin;

import java.sql.Date;

public class LecRecord {
    private String lecturerID;
    private String lecturerName;
    private String lecturerEmail;
    private String lecturerPhone;
    private String field;
    private Date joinDate;
    public LecRecord(String lecturerID, String lecturerName, String lecturerEmail, String lecturerPhone, String field, Date joinDate) {
        this.lecturerID = lecturerID;
        this.lecturerName = lecturerName;
        this.lecturerEmail = lecturerEmail;
        this.lecturerPhone = lecturerPhone;
        this.field = field;
        this.joinDate = joinDate;
    }

    public String getLecturerID() {
        return lecturerID;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public String getLecturerEmail() {
        return lecturerEmail;
    }

    public String getLecturerPhone() {
        return lecturerPhone;
    }

    public String getField() {
        return field;
    }

    public Date getJoinDate() {
        return joinDate;
    }
}
