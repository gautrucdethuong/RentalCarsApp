package com.example.rentalcarsapp.model;

import java.util.Date;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 6/30/2021.
 * Company: FPT大学.
 */

public class Booking {
    private Date bookingPickUpDate;
    private Date bookingDropOffDate;
    private Double bookingTotal;
    private int bookingStatus;

    public Booking() {

    }

    public Booking(Date bookingPickUpDate, Date bookingDropOffDate, Double bookingTotal, int bookingStatus) {
        this.bookingPickUpDate = bookingPickUpDate;
        this.bookingDropOffDate = bookingDropOffDate;
        this.bookingTotal = bookingTotal;
        this.bookingStatus = bookingStatus;
    }

    public Date getBookingPickUpDate() {
        return bookingPickUpDate;
    }

    public void setBookingPickUpDate(Date bookingPickUpDate) {
        this.bookingPickUpDate = bookingPickUpDate;
    }

    public Date getBookingDropOffDate() {
        return bookingDropOffDate;
    }

    public void setBookingDropOffDate(Date bookingDropOffDate) {
        this.bookingDropOffDate = bookingDropOffDate;
    }

    public Double getBookingTotal() {
        return bookingTotal;
    }

    public void setBookingTotal(Double bookingTotal) {
        this.bookingTotal = bookingTotal;
    }

    public int getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(int bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
