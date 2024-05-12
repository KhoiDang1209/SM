package Controller.Tuition;

import java.sql.Date;

public class TuitionRecord extends Tuition {
    private String studentID;
    private String tuitionID;
    private int amount;
    private Date paymentDate;
    private String semester;
    public TuitionRecord(String studentID, String tuitionID, int amount, Date paymentDate,String semester) {
        this.studentID = studentID;
        this.tuitionID = tuitionID;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.semester = semester;
    }
    public void setStudentID(String studentID) {}
    public void setTuitionID(String tuitionID) {}
    public void setAmount(int amount) {}
    public void setPaymentDate(Date paymentDate) {}
    public String getStudentID() {return studentID;}
    public String getTuitionID() {return tuitionID;}
    public int getAmount() {return amount;}
    public Date getPaymentDate() {return paymentDate;}
    public String getSemester() {return semester;}
    // Getters and setters
}

