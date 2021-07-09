package com.example.rentalcarsapp.model;

import java.util.Date;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 6/30/2021.
 * Company: FPT大学.
 */

public class Bill {
private String bookingId;
private String billId;
private Date billCreateDate;
private Date billUpdatedDate;
private int billStatus;
    public Bill() {

    }

    public Bill(String bookingId, String billId, Date billCreateDate, Date billUpdatedDate, int billStatus) {
        this.bookingId = bookingId;
        this.billId = billId;
        this.billCreateDate = billCreateDate;
        this.billUpdatedDate = billUpdatedDate;
        this.billStatus = billStatus;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public Date getBillCreateDate() {
        return billCreateDate;
    }

    public void setBillCreateDate(Date billCreateDate) {
        this.billCreateDate = billCreateDate;
    }

    public Date getBillUpdatedDate() {
        return billUpdatedDate;
    }

    public void setBillUpdatedDate(Date billUpdatedDate) {
        this.billUpdatedDate = billUpdatedDate;
    }

    public int getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(int billStatus) {
        this.billStatus = billStatus;
    }
}
