package com.example.rentalcarsapp.model;

import java.util.Date;

/**
 * Author by Tieu Ha Anh Khoi
 * Email: khoithace140252@fpt.edu.vn.
 * Date on 7/4/2021.
 * Company: FPT大学.
 */

public class Bill {
private String bookingId;//declare variable bookingId
private String billId;//declare variable billId
private Date billCreateddate;//declare variable billCreateddate
private Date billUpdateddate;//declare variable billUpdateddate
private int billStatus;//declare variable billStatus
public float bookingTotal;//declare variable bookingTotal

    /**
     * Create a constructor with no parameters
     */
    public Bill() {

    }

    /**
     * Create a constructor with full parameters
     * @param bookingId id of booking to store in database
     * @param billId id of bill to store in database
     * @param billCreateddate create date of bill to store in database
     * @param billUpdateddate update date of bill to store in database
     * @param billStatus status of bill to store in database
     * @param bookingTotal total of booking to store in database
     */
    public Bill(String bookingId, String billId, Date billCreateddate, Date billUpdateddate, int billStatus,float bookingTotal) {
        this.bookingId = bookingId;
        this.billId = billId;
        this.billCreateddate = billCreateddate;
        this.billUpdateddate = billUpdateddate;
        this.billStatus = billStatus;
        this.bookingTotal=bookingTotal;
    }

    /**
     * Get total of booking
     * @return bookingTotal
     */
    public float getBookingTotal() {
        return bookingTotal;
    }

    /**
     * Set total of booking
     * @param bookingTotal total of booking to store in database
     */
    public void setBookingTotal(float bookingTotal) {
        this.bookingTotal = bookingTotal;
    }

    /**
     * Get id of booking
     * @return bookingId
     */
    public String getBookingId() {
        return bookingId;
    }
    /**
     * Set total of booking
     * @param bookingId id of booking to store in database
     */
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    /**
     * Get id of bill
     * @return billId
     */
    public String getBillId() {
        return billId;
    }

    /**
     * Set id of bill
     * @param billId id of bill to store in database
     */
    public void setBillId(String billId) {
        this.billId = billId;
    }

    /**
     * Get create date of bill
     * @return billCreateddate
     */
    public Date getBillCreatedDate() {
        return billCreateddate;
    }

    /**
     * Set create date of bill
     * @param billCreateddate create date of bill to store in database
     */
    public void setBillCreatedDate(Date billCreateddate) {
        this.billCreateddate = billCreateddate;
    }

    /**
     * Get update date of bill
     * @return billUpdateddate
     */
    public Date getBillUpdatedDate() {
        return billUpdateddate;
    }

    /**
     * Set update date of bill
     * @param billUpdatedDate update date of bill to store in database
     */
    public void setBillUpdatedDate(Date billUpdatedDate) {
        this.billUpdateddate = billUpdateddate;
    }

    /**
     * Get status of bill
     * @return billStatus
     */
    public int getBillStatus() {
        return billStatus;
    }

    /**
     * Set status of bill
     * @param billStatus status of bill to store in database
     */
    public void setBillStatus(int billStatus) {
        this.billStatus = billStatus;
    }
}
