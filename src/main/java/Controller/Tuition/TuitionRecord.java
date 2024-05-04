package Controller.Tuition;

import java.sql.Date;

public class TuitionRecord extends Tuition {
    private String studentID;
    private String tuitionID;
    private int amount;
    private Date paymentDate;

    public TuitionRecord(String studentID, String tuitionID, int amount, Date paymentDate) {
        this.studentID = studentID;
        this.tuitionID = tuitionID;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }
    public void setStudentID(String studentID) {}
    public void setTuitionID(String tuitionID) {}
    public void setAmount(int amount) {}
    public void setPaymentDate(Date paymentDate) {}
    public String getStudentID() {return studentID;}
    public String getTuitionID() {return tuitionID;}
    public int getAmount() {return amount;}
    public Date getPaymentDate() {return paymentDate;}
    // Getters and setters
}

